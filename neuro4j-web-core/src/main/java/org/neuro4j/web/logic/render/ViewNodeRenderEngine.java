/*
 * Copyright (c) 2013-2016, Neuro4j
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.neuro4j.web.logic.render;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.neuro4j.workflow.FlowContext;

/**
 * Interface for custom ViewNode render engine
 * 
 * 
 */
public interface ViewNodeRenderEngine {

    /**
     * 
     * 
     * @param response
     * @param servletContext
     * @param logicContext
     * @param viewTemplate
     * @throws IOException
     */
    public void render(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext, FlowContext logicContext, String viewTemplate) throws ViewNodeRenderExecutionException;

    public void init(ServletConfig config, ServletContext servletContext) throws ViewNodeRenderExecutionException;

}
