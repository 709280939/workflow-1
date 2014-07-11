/*
 * Copyright (c) 2013-2014, Neuro4j
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
package tests.org.neuro4j.jasper.reports;

import java.util.Collections;

import junit.framework.TestCase;

import org.junit.Test;
import org.neuro4j.workflow.ExecutionResult;
import org.neuro4j.workflow.common.WorkflowEngine;

/**
 *
 */
public class PdfReportTestCase extends TestCase {

    @Test
    public void testPdfCreation() {
        ExecutionResult result = WorkflowEngine.run("tests.org.neuro4j.jasper.reports.GenerateReport-Start", Collections.EMPTY_MAP);
        System.out.println();

    }

}
