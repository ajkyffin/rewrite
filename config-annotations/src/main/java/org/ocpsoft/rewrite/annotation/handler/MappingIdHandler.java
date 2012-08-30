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
package org.ocpsoft.rewrite.annotation.handler;

import org.ocpsoft.rewrite.annotation.MappingId;
import org.ocpsoft.rewrite.annotation.api.ClassContext;
import org.ocpsoft.rewrite.annotation.api.HandlerChain;
import org.ocpsoft.rewrite.annotation.spi.AnnotationHandler;

public class MappingIdHandler implements AnnotationHandler<MappingId>
{

   @Override
   public Class<MappingId> handles()
   {
      return MappingId.class;
   }

   @Override
   public int priority()
   {
      return HandlerWeights.WEIGHT_TYPE_ENRICHING;
   }

   @Override
   public void process(ClassContext context, MappingId annotation, HandlerChain chain)
   {
      context.getRuleBuilder().withId(annotation.value());
      chain.proceed();
   }

}