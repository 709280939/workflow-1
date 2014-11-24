package org.neuro4j.springframework.jms.example;

import static org.neuro4j.springframework.jms.example.DoSomething.IN_MESSAGE;

import javax.jms.Message;

import org.neuro4j.workflow.FlowContext;
import org.neuro4j.workflow.common.FlowExecutionException;
import org.neuro4j.workflow.common.ParameterDefinition;
import org.neuro4j.workflow.common.ParameterDefinitionList;
import org.neuro4j.workflow.log.Logger;
import org.neuro4j.workflow.node.CustomBlock;

@ParameterDefinitionList(input = { @ParameterDefinition(name = IN_MESSAGE, isOptional = true, type = "javax.jms.Message") }, output = {})
public class DoSomething extends CustomBlock {

	static final String IN_MESSAGE = "message";

	public int execute(FlowContext ctx) throws FlowExecutionException {

		Message message = (Message) ctx.get(IN_MESSAGE);

		Logger.info(this, "Working with message {}", message);

		return NEXT;
	}

}
