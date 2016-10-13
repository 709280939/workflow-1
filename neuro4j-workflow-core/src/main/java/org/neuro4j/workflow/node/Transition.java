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

package org.neuro4j.workflow.node;

import org.neuro4j.workflow.common.Workflow;

public class Transition {

    private String uuid;
    private String name;
    private Workflow workflow;
    private WorkflowNode fromNode;
    private WorkflowNode toNode;

    public Transition(Workflow workflow) {
        this.workflow = workflow;
    }

    public WorkflowNode getFromNode() {
        return fromNode;
    }

    public void setFromNode(WorkflowNode fromNode) {
        this.fromNode = fromNode;
    }

    public WorkflowNode getToNode() {
        return toNode;
    }

    public void setToNode(WorkflowNode toNode) {
        this.toNode = toNode;
    }

    public boolean isValid() {
        if (fromNode == null || toNode == null) {
            return false;
        }

        return true;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

}
