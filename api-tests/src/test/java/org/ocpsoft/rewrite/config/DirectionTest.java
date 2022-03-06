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
package org.ocpsoft.rewrite.config;

import org.junit.Test;

import org.ocpsoft.rewrite.mock.MockEvaluationContext;
import org.ocpsoft.rewrite.test.MockInboundRewrite;
import org.ocpsoft.rewrite.test.MockOutboundRewrite;
import org.ocpsoft.rewrite.test.MockRewrite;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
public class DirectionTest
{

   @Test
   public void testInboundReturnsTrue()
   {
      Condition condition = Direction.isInbound();
      assertThat(condition.evaluate(new MockInboundRewrite(), new MockEvaluationContext())).isTrue();
   }

   @Test
   public void testNotInboundReturnsFalse()
   {
      Condition condition = Direction.isInbound();
      assertThat(condition.evaluate(new MockRewrite(), new MockEvaluationContext())).isFalse();
   }

   @Test
   public void testOutboundReturnsTrue()
   {
      Condition condition = Direction.isOutbound();
      assertThat(condition.evaluate(new MockOutboundRewrite(), new MockEvaluationContext())).isTrue();
   }

   @Test
   public void testNotOutboundReturnsFalse()
   {
      Condition condition = Direction.isOutbound();
      assertThat(condition.evaluate(new MockRewrite(), new MockEvaluationContext())).isFalse();
   }
}
