/**
 * 
 */
package org.neuro4j.workflow.guice;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.neuro4j.workflow.FlowContext;
import org.neuro4j.workflow.WorkflowRequest;
import org.neuro4j.workflow.common.FlowInitializationException;
import org.neuro4j.workflow.common.WorkflowEngine;
import org.neuro4j.workflow.guice.flows.CustomBlockWithService;
import org.neuro4j.workflow.log.Logger;

import com.google.inject.Module;

/**
 *
 *
 */
public class GuiceWithWorkflowTestCase {

    GuiceCustomBlockInitStrategy initStrategy = null;
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        // Create guice module
        AppInjector module = new AppInjector();
        
        List<Module> modules = new ArrayList<Module>();
        
        modules.add(module);
        
        // Register Guice strategy in workflow engine.
        initStrategy = new GuiceCustomBlockInitStrategy(modules);
        
        WorkflowEngine.setCustomBlockInitStrategy(initStrategy);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testFlow() {
        // Now all custom blocks will be initialized with Guice.
        WorkflowEngine.run("org.neuro4j.workflow.guice.flows.Flow-Start", new WorkflowRequest());
    }
    
    @Test
    public void testCustomBlock() {
        try {
            CustomBlockWithService cb = (CustomBlockWithService) initStrategy.loadCustomBlock(CustomBlockWithService.class.getCanonicalName());
            cb.execute(new FlowContext());
        } catch (Exception e) {
            Logger.error(this, e);
            fail();
        }
    }

}
