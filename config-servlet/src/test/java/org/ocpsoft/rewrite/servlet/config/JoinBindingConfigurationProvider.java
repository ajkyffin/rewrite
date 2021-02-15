/*
 * Copyright 2012 <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
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

import javax.servlet.ServletContext;

import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.context.EvaluationContext;
import org.ocpsoft.rewrite.servlet.config.bind.RequestBinding;
import org.ocpsoft.rewrite.servlet.config.rule.Join;
import org.ocpsoft.rewrite.servlet.http.event.HttpServletRewrite;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
public class JoinBindingConfigurationProvider extends HttpConfigurationProvider
{
   @Override
   public int priority()
   {
      return 0;
   }

   @Override
   public Configuration getConfiguration(final ServletContext context)
   {
      Configuration config = ConfigurationBuilder.begin()
               .addRule(Join.path("/bind/{id}").to("/bind.html"))
               .where("id").bindsTo(RequestBinding.parameter("join-id"))
               
               .addRule(Join.path("/users/{userId}").to("/user.html"))
               .perform(new HttpOperation() {
                  @Override
                  public void performHttp(HttpServletRewrite event, EvaluationContext context)
                  {
                     for (String userId : event.getRequest().getParameterValues("userId")) {
                        event.getResponse().addHeader("userId", userId);
                     }
                     SendStatus.code(200).perform(event, context);
                  }
               })
              
               .addRule()
               .when(Path.matches("/bind.html").and(RequestParameter.exists("join-id")))
               .perform(SendStatus.code(201));
      return config;
   }
}
