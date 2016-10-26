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
package org.neuro4j.workflow.node;

import org.neuro4j.workflow.FlowContext;
import org.neuro4j.workflow.WorkflowRequest;
import org.neuro4j.workflow.common.FlowExecutionException;
import org.neuro4j.workflow.common.FlowInitializationException;
import org.neuro4j.workflow.common.Workflow;

/**
 * CallNode calls other workflow.
 * 
 */
public class CallNode extends WorkflowNode {

    private String callFlow;
    private String dynamicFlownName;

    public CallNode(String name, String uuid)
    {
        super(name, uuid);
    }

    /**
     * Returns flow's name which should be called
     * @return flow's name 
     */
    public String getCallFlow() {
        return callFlow;
    }

    public void setCallFlow(String callFlow) {
        this.callFlow = callFlow;
    }

    public String getDynamicFlownName() {
        return dynamicFlownName;
    }

    public void setDynamicFlownName(String dynamicFlownName) {
        this.dynamicFlownName = dynamicFlownName;
    }

    @Override
    final public void validate(final WorkflowProcessor processor, FlowContext ctx) throws FlowExecutionException
    {

        if (dynamicFlownName == null && callFlow == null)
        {
            throw new FlowExecutionException("CallByFlowName node: Flow not defined.");
        }

    }

    @Override
    public final Transition execute(final WorkflowProcessor processor, final WorkflowRequest request) throws FlowExecutionException {
        FlowContext ctx = request.getLogicContext();
        String flow = callFlow;
        if (dynamicFlownName != null)
        {
            flow = (String) ctx.get(dynamicFlownName);
        }

        if (flow == null)
        {
            throw new FlowExecutionException("CallNode: Flow not defined.");
        }
        
        FlowParameter flowParameter  = FlowParameter.parse(flow);

        Workflow calledWorkflow  = processor.loadWorkflow(flowParameter.getFlowName());

        if (calledWorkflow == null)
        {
            throw new FlowExecutionException(flowParameter.getFlowName() + " not found.");
        }

        StartNode startNode = calledWorkflow.getStartNode(flowParameter.getStartNode());
        if (startNode == null)
        {
            throw new FlowExecutionException(new StringBuilder(flowParameter.getStartNode()).append(" not found in flow ").append(flowParameter.getFlowName()).toString());
        }
        
        if (!startNode.isPublic())
        {
            if (!request.getCurrentPackage().equals(calledWorkflow.getPackage()))
            {
                throw new FlowExecutionException(new StringBuilder("Node ").append(startNode.getName()).append(" in package ").append(calledWorkflow.getPackage()).append(" is private and can be used just inside package.").toString());
            }
        }

        try {

            request.pushPackage(calledWorkflow.getPackage());

            processor.executeWorkflow(startNode, request);

            request.popPackage();
        } catch (FlowExecutionException exeption) {
            request.popPackage();
            throw exeption;
        }

        WorkflowNode endNode = request.getLastSuccessfulNode();

        Transition nextNode = getExitByName(endNode.getName());

        if (nextNode == null)
        {
            if (getExits().size() == 1) {
                nextNode = getExits().iterator().next();

            }
            
        }
        
        if (nextNode != null)
        {
            request.setNextRelation(nextNode);
        }


        return nextNode;
    }


    @Override
    public final void init() throws FlowInitializationException
    {

    }

}
