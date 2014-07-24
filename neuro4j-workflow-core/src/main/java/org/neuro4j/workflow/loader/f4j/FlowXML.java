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

package org.neuro4j.workflow.loader.f4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "flow")
public class FlowXML {

    @XmlAttribute
    public String visibility;

    @XmlElementWrapper(name = "nodes")
    @XmlElement(name = "node")
    List<NodeXML> nodes = new ArrayList<NodeXML>();

    public FlowXML()
    {

    }

    public List<NodeXML> getEntities() {
        return nodes;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Collection<NodeXML> getXmlNodes() {
        return nodes;
    }

}
