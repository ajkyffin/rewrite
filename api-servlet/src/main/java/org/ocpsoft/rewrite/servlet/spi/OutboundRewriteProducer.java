/*
 * Copyright 2011 <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
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
package org.ocpsoft.rewrite.servlet.spi;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import org.ocpsoft.common.pattern.Specialized;
import org.ocpsoft.common.pattern.Weighted;
import org.ocpsoft.rewrite.servlet.event.OutboundServletRewrite;

/**
 * SPI for creating {@link OutboundRewriteEvent} instances.
 * 
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
public interface OutboundRewriteProducer<IN extends ServletRequest, OUT extends ServletResponse, PAYLOADTYPE> extends
         Specialized<PAYLOADTYPE>, Weighted
{
   /**
    * Create an {@link OutboundServletRewrite} instance.
    */
   OutboundServletRewrite<IN, OUT, PAYLOADTYPE> createOutboundRewrite(ServletRequest request, ServletResponse response,
            ServletContext servletContext, PAYLOADTYPE payload);
}
