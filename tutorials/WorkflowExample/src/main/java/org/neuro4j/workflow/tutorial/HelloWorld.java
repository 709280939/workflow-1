package org.neuro4j.workflow.tutorial;

import org.neuro4j.workflow.FlowContext;
import org.neuro4j.workflow.common.FlowExecutionException;
import org.neuro4j.workflow.common.FlowInitializationException;
import org.neuro4j.workflow.common.ParameterDefinition;
import org.neuro4j.workflow.common.ParameterDefinitionList;
import org.neuro4j.workflow.node.CustomBlock;
import static org.neuro4j.workflow.tutorial.HelloWorld.*;

@ParameterDefinitionList(input = { @ParameterDefinition(name = IN_NAME, isOptional = true, type = "java.lang.String") }, 
                         output = { @ParameterDefinition(name = OUT_MESSAGE, isOptional = true, type = "java.lang.String") })
public class HelloWorld extends CustomBlock {

	static final String IN_NAME = "name";

	static final String OUT_MESSAGE = "message";

	public int execute(FlowContext ctx) throws FlowExecutionException {

		String name = (String) ctx.get(IN_NAME);

		String message = "Hello World! ";

		if (name != null) {
			message += name;
		}

		ctx.put(OUT_MESSAGE, message);

		return NEXT;
	}

	@Override
	public void init() throws FlowInitializationException {
		super.init();
	}

}
