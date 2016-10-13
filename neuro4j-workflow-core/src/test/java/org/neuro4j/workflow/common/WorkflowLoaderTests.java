package org.neuro4j.workflow.common;

import org.junit.Test;

import static junit.framework.Assert.*;


public class WorkflowLoaderTests {
	
	@Test
	public void testDefaultClassPathLoader() throws FlowExecutionException{
		ClasspathWorkflowLoader loader = new ClasspathWorkflowLoader();
		WorkflowSource workflowSource = loader.load("org.neuro4j.workflow.flows.FlowForClasspathLoader");
		assertNotNull(workflowSource);
		Workflow flow = workflowSource.content();
		assertNotNull(flow);
	}
	
	@Test
	public void testClassPathLoader() throws FlowExecutionException{
		ClasspathWorkflowLoader loader = new ClasspathWorkflowLoader(".n4j");
		WorkflowSource workflowSource = loader.load("org.neuro4j.workflow.flows.FlowForClasspathLoader");
		assertNotNull(workflowSource);
		Workflow flow = workflowSource.content();
		assertNotNull(flow);
		assertEquals("FlowForClasspathLoader", flow.getFlowName());
		//assertEquals("org.neuro4j.workflow.flows", flow.getPackage());
	}

}
