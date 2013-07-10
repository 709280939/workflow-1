/**
 * 
 */
package org.neuro4j.flows.nodes.callnode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.neuro4j.logic.LogicContext;
import org.neuro4j.logic.swf.FlowExecutionException;
import org.neuro4j.logic.swf.SimpleWorkflowException;
import org.neuro4j.tests.base.BaseFlowTestCase;

/**
 *
 */
public class CallNodeTestCase extends BaseFlowTestCase{



	@Test
	public void testCallOtherFlow() {
		try {
			
			LogicContext logicContext = executeFlow("org.neuro4j.flows.nodes.callnode.CallByNameFlow-StartNode1");
			String var2 = (String)logicContext.get("var1");

			assertEquals(var2, "test value");

		} catch (FlowExecutionException e) {
			fail(e.toString());
		}
	}

	@Test
	public void testCallWrongNode() {
		try {
			
			LogicContext logicContext = executeFlow("org.neuro4j.flows.nodes.callnode.CallByNameFlow-StartNode2");

			fail("Wrong node name");

		} catch (FlowExecutionException e) {
			e.printStackTrace();
			Assert.assertTrue( e instanceof FlowExecutionException);
			Assert.assertTrue( e.getMessage().equals("org/neuro4j/flows/nodes/CallByNameFlow2 not found."));
			
		}
	}
	
	@Test
	public void testCallDynamicNode() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("dynamicNodeName", "org.neuro4j.flows.nodes.callnode.CallByNameFlow2-StartNode2");
			
			String var2 = (String)executeFlowAndReturnObject("org.neuro4j.flows.nodes.callnode.CallByNameFlow-StartNode3", params, "var1");
			
			assertEquals(var2, "StartNode2");

		} catch (FlowExecutionException e) {
			fail(e.toString());
		}
	}

}
