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
package org.neuro4j.workflow.loader;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.neuro4j.workflow.common.FlowExecutionException;
import org.neuro4j.workflow.common.Workflow;

/**
 * Loads workflow from memory.
 *
 */
public class InMemoryWorkflowLoader implements WorkflowLoader{

	private final ConcurrentMap<String, Workflow> cache = new ConcurrentHashMap<>();
	private final WorkflowLoader delegate;
	
	public InMemoryWorkflowLoader(final WorkflowLoader loader){
		this.delegate = loader;
	}
	
	@Override
	public Workflow load(String name) throws FlowExecutionException {
		Workflow workflow = cache.get(name);
		if (workflow == null){
			workflow = delegate.load(name);
		}
		return workflow;
	}

	public void register(Workflow workflow){
		cache.put(workflow.getFlowName(), workflow);
	}
	
	
}
