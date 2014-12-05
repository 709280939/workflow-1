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


package org.neuro4j.workflow.loader;

import org.apache.commons.beanutils.ConstructorUtils;
import org.neuro4j.workflow.common.FlowInitializationException;
import org.neuro4j.workflow.log.Logger;
import org.neuro4j.workflow.node.CustomBlock;

/**
 * This class provides default implementation of CustomBlockInitStrategy.
 *
 */
public class DefaultCustomBlockInitStrategy implements CustomBlockInitStrategy {

    @Override
    public CustomBlock loadCustomBlock(String className) throws FlowInitializationException {
        try {

            Class<? extends CustomBlock> clazz = (Class<? extends CustomBlock>) Class.forName(className);
            if (null != clazz)
            {
                CustomBlock customBlock  = (CustomBlock) ConstructorUtils.invokeConstructor(clazz, null);
                return customBlock;
            }

        } catch (Exception e) {
            Logger.error(this, e);
            throw new FlowInitializationException(e);
        }
        throw new FlowInitializationException("CustomBlock: " + className + " can not be initialized.");
    }
    
   
    public Class<? extends CustomBlock> getCustomBlockClass(String className) throws FlowInitializationException {
        try {

            Class<? extends CustomBlock> clazz = (Class<? extends CustomBlock>) Class.forName(className);
            if (null != clazz)
            {
                return clazz;
            }

        } catch (Exception e) {
            Logger.error(this, e);
            throw new FlowInitializationException(e);
        }
        throw new FlowInitializationException("CustomBlock: " + className + " can not be initialized.");
    }

}
