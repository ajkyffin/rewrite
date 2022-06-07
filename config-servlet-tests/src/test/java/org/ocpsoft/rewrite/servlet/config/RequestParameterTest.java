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
package org.ocpsoft.rewrite.servlet.config;

import java.util.Arrays;
import java.util.Collections;

import jakarta.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ocpsoft.rewrite.event.Rewrite;
import org.ocpsoft.rewrite.mock.MockEvaluationContext;
import org.ocpsoft.rewrite.mock.MockRewrite;
import org.ocpsoft.rewrite.param.DefaultParameterStore;
import org.ocpsoft.rewrite.param.Parameter;
import org.ocpsoft.rewrite.param.ParameterConfiguration;
import org.ocpsoft.rewrite.param.ParameterStore;
import org.ocpsoft.rewrite.param.RegexConstraint;
import org.ocpsoft.rewrite.servlet.impl.HttpInboundRewriteImpl;
import org.ocpsoft.rewrite.util.ParameterUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
public class RequestParameterTest
{
   private Rewrite rewrite;
   private HttpServletRequest request;

   @Before
   public void before()
   {
      request = Mockito.mock(HttpServletRequest.class);
      Mockito.when(request.getParameterNames())
               .thenReturn(Collections.enumeration(Arrays.asList("foo", "baz")));

      Mockito.when(request.getParameterValues("foo"))
               .thenReturn(new String[] { "bar" });

      Mockito.when(request.getParameterValues("baz"))
               .thenReturn(new String[] { "cab", "caz" });

      Mockito.when(request.getParameter("foo"))
               .thenReturn("bar");

      Mockito.when(request.getParameter("baz"))
               .thenReturn("cab");

      rewrite = new HttpInboundRewriteImpl(request, null, null);
   }

   @Test
   public void testRequestParameterExists()
   {
      RequestParameter parameter = RequestParameter.exists("foo");
      MockEvaluationContext context = new MockEvaluationContext();
      ParameterUtils.initialize(context, parameter);
      assertThat(parameter.evaluate(rewrite, context)).isTrue();
   }

   @Test
   public void testRequestParameterExists2()
   {
      RequestParameter parameter = RequestParameter.exists("baz");
      MockEvaluationContext context = new MockEvaluationContext();
      ParameterUtils.initialize(context, parameter);
      assertThat(parameter.evaluate(rewrite, context)).isTrue();
   }

   @Test
   public void testRequestParameterExistsFalse()
   {
      assertThat(RequestParameter.exists("nope").evaluate(rewrite, new MockEvaluationContext())).isFalse();
   }

   @Test
   public void testRequestParameterContains()
   {
      RequestParameter parameter = RequestParameter.valueExists("bar");
      MockEvaluationContext context = new MockEvaluationContext();
      ParameterUtils.initialize(context, parameter);
      assertThat(parameter.evaluate(rewrite, context)).isTrue();
   }

   @Test
   public void testRequestParameterMatches()
   {
      RequestParameter parameter = RequestParameter.matches("foo", "{value}");

      MockEvaluationContext context = new MockEvaluationContext();
      ParameterUtils.initialize(context, parameter);
      assertThat(parameter.evaluate(rewrite, context)).isTrue();

      ParameterStore store = DefaultParameterStore.getInstance(context);
      Parameter<?> p = store.get("value");
      ((ParameterConfiguration<?>) p).constrainedBy(new RegexConstraint("(bar|baz)"));
      assertThat(parameter.evaluate(rewrite, context)).isFalse();
   }

   @Test
   public void testRequestParameterMatchesAll()
   {
      RequestParameter requestParam = RequestParameter.matchesAll("baz", "{*}");

      MockEvaluationContext context = new MockEvaluationContext();
      ParameterUtils.initialize(context, requestParam);

      assertThat(requestParam.evaluate(rewrite, context)).isTrue();
   }

   @Test
   public void testRequestParameterMatchesAllNamesAllValues()
   {
      RequestParameter parameter = RequestParameter.matchesAll("{name}", "{value}");
      MockEvaluationContext context = new MockEvaluationContext();
      ParameterUtils.initialize(context, parameter);
      assertThat(parameter.evaluate(rewrite, context)).isTrue();
   }

   @Test
   public void testRequestParameterMatchesAllNamesNotValues()
   {
      RequestParameter parameter = RequestParameter.matchesAll("{name}", "{value}");
      MockEvaluationContext context = new MockEvaluationContext();
      ParameterUtils.initialize(context, parameter);

      ParameterStore store = DefaultParameterStore.getInstance(context);
      ((ParameterConfiguration<?>) store.get("value")).constrainedBy(new RegexConstraint("nothing"));
      assertThat(parameter.evaluate(rewrite, context)).isFalse();
   }

   @Test
   public void testRequestParameterMatchesAllNotName()
   {
      RequestParameter requestParam = RequestParameter.matchesAll("{name}", "{value}");
      MockEvaluationContext context = new MockEvaluationContext();
      ParameterUtils.initialize(context, requestParam);

      ParameterStore store = DefaultParameterStore.getInstance(context);
      ((ParameterConfiguration<?>) store.get("name")).constrainedBy(new RegexConstraint("nothing"));
      assertThat(requestParam.evaluate(rewrite, new MockEvaluationContext())).isFalse();
   }

   @Test
   public void testRequestParameterMatchesAllInvalid()
   {
      RequestParameter requestParam = RequestParameter.matchesAll("baz", "{value}");

      MockEvaluationContext context = new MockEvaluationContext();
      ParameterUtils.initialize(context, requestParam);
      ParameterStore store = DefaultParameterStore.getInstance(context);

      ((ParameterConfiguration<?>) store.get("value")).constrainedBy(new RegexConstraint("(cab|xxx)"));
      assertThat(requestParam.evaluate(rewrite, context)).isFalse();
   }

   @Test
   public void testBadRegexThrowsException()
   {
      assertThat(RequestParameter.matches(".*", "bar").evaluate(rewrite, new MockEvaluationContext())).isFalse();
   }

   @Test(expected = IllegalArgumentException.class)
   public void testNullNameInput()
   {
      RequestParameter.exists(null);
   }

   @Test(expected = IllegalArgumentException.class)
   public void testNullValueExistsInput()
   {
      RequestParameter.valueExists(null);
   }

   @Test(expected = IllegalArgumentException.class)
   public void testNullInputs()
   {
      RequestParameter.matches(null, null);
   }

   @Test
   public void testDoesNotMatchNonHttpRewrites()
   {
      assertThat(RequestParameter.exists("foo").evaluate(new MockRewrite(), new MockEvaluationContext())).isFalse();
   }
}
