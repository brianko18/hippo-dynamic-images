/*
 * Copyright 2017 Open Web IT B.V. (https://www.openweb.nl/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.openweb.hippo.dynamicimage;

import nl.openweb.hippo.dynamicimage.strategy.VariantStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;


/**
 * Job that retrieves the node of the requested variant. The Node is created when it did not exist.
 * 
 * @author Ivor Boers
 *
 */
public class ImageVariantJob  extends RepositoryJob<Node> {
    private static final Logger LOG = LoggerFactory.getLogger(ImageVariantJob.class);

    private final VariantStrategy variantStrategy;
    private final Node sourceVariantNode;
    private final int width;
    private final int height;
    private final String variantName;

    /**
     * Job to write an imagevariant to the repository
     * @param variantStrategy variantStrategy
     * @param sourceVariantNode the node of the variant that is the source
     * @param width 0 or lower means not specified
     * @param height 0 or lower means not specified
     * @param variantName name of the node stored in the repository
     */
    public ImageVariantJob(VariantStrategy variantStrategy, Node sourceVariantNode, String variantName, int width, int height) {
        super();
        this.variantStrategy = variantStrategy;
        this.sourceVariantNode = sourceVariantNode;
        this.width = width;
        this.height = height;
        this.variantName = variantName;
    }

    @Override
    public Node doJob(Session session)  throws JobExecutionException{
        try {
            // get the node in the writable session
            Node node = session.getNodeByIdentifier(sourceVariantNode.getIdentifier());
            return variantStrategy.createVariant(node, variantName, width, height);
        } catch (RepositoryException e) {
            throw new JobExecutionException("Failed to create dynamic imagevariant", e);
        }
    }

}
