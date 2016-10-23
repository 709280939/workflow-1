/**
 * Copyright (c) 2013-2016, Neuro4j
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.neuro4j.web.render.jasper;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.neuro4j.web.logic.WebFlowConstants;
import org.neuro4j.web.logic.render.ViewNodeRenderEngine;
import org.neuro4j.web.logic.render.ViewNodeRenderExecutionException;
import org.neuro4j.workflow.FlowContext;

/**
 * Class generates document in different formats.
 *
 */
public class JasperViewNodeRenderEngine implements ViewNodeRenderEngine {

    private static final String IN_JASPER_PARAMETERS = "jasperParameters";
    private static final String IN_JASPER_FORMAT = "jasperFormat";
    private static final String IN_JASPER_FILE_NAME = "jasperFileName";
    private static final String IN_JASPER_DATASOURCE = "jasperDataSource";
    private static final String DEFAULT_FORMAT = "pdf";

    @Override
    public void init(ServletConfig config, ServletContext servletContext) throws ViewNodeRenderExecutionException {

    }

    @Override
    public void render(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext, FlowContext logicContext, String view) throws ViewNodeRenderExecutionException
    {
        
        // handle if view is not specified -> go to default page with trace
        if (null == view) {
            view = WebFlowConstants.DEFAULT_VIEW_PAGE;
        } else if (view.startsWith("/")) {
            view = WebFlowConstants.DEFAULT_VIEW_DIRECTORY + view.replaceFirst("/", "");
        } else {
            view = WebFlowConstants.DEFAULT_VIEW_DIRECTORY + view;
        }
        
        Map<String, Object> parameters = (Map<String, Object>) logicContext.get(IN_JASPER_PARAMETERS);

        if (parameters == null)
        {
            parameters = new HashMap<String, Object>();
        }

        String format = (String) logicContext.get(IN_JASPER_FORMAT);
        if (format == null)
        {
            format = DEFAULT_FORMAT;
        }

        JRDataSource dataSource = (JRDataSource) logicContext.get(IN_JASPER_DATASOURCE);
        if (dataSource == null)
        {
            dataSource = new JREmptyDataSource();
        }

        if (view == null)
        {
            throw new ViewNodeRenderExecutionException("JasperReports template is null");
        }

        InputStream inputStream = servletContext.getResourceAsStream(view);

        if (inputStream == null)
        {
            throw new ViewNodeRenderExecutionException("JasperReports template has not been loaded. Template path:" + view);
        }

        response.setContentType(getContentType(format));

        String fileName = (String) logicContext.get(IN_JASPER_FILE_NAME);
        if (fileName == null)
        {
            fileName = "report." + format;
        }

        JasperPrint jasperPrint = null;
        try {
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(inputStream);

            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            JRAbstractExporter exporter = getJRExporter(format);
            if (exporter == null)
            {
                throw new ViewNodeRenderExecutionException("JRAbstractExporter not found for format: " + format);
            }

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output);
            exporter.exportReport();
            byte[] report = output.toByteArray();
            response.setContentLength(report.length);
            response.setHeader("Content-Disposition", "filename=\"" + fileName + "\";");

            response.getOutputStream().write(report);

        } catch (Exception e) {
            throw new ViewNodeRenderExecutionException(e.getMessage());
        }

        return;
    }

    private JRAbstractExporter getJRExporter(String format) {
        JRAbstractExporter exporter = null;
        if (format.equals("pdf")) {
            exporter = new JRPdfExporter();
        } else if (format.equals("rtf")) {
            exporter = new JRRtfExporter();
        } else if (format.equals("html")) {
            exporter = new JRHtmlExporter();
        } else if (format.equals("docx")) {
            exporter = new JRDocxExporter();
        } else if (format.equals("pptx")) {
            exporter = new JRPptxExporter();
        }

        return exporter;
    }

    private String getContentType(String fileType) {

        if (fileType == null) {
            return "application/octet-stream";
        }

        if (fileType.toLowerCase().contains("txt"))
            return "text/plain";
        else if (fileType.toLowerCase().contains("docx"))
            return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        else if (fileType.toLowerCase().contains("doc"))
            return "application/msword";
        else if (fileType.toLowerCase().contains("xlsx"))
            return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        else if (fileType.toLowerCase().contains("xls"))
            return "application/vnd.ms-excel";
        else if (fileType.toLowerCase().contains("pdf"))
            return "application/pdf";
        else if (fileType.toLowerCase().contains("pptx"))
            return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
        else if (fileType.toLowerCase().contains("ppt"))
            return "application/ppt";
        else if (fileType.toLowerCase().contains("zip"))
            return "application/zip";
        else if (fileType.toLowerCase().contains("rtf"))
            return "application/rtf";

        else
            return "application/octet-stream";
    }

}
