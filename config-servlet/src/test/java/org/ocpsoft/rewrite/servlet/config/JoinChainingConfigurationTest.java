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

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ocpsoft.rewrite.config.ConfigurationProvider;
import org.ocpsoft.rewrite.test.HttpAction;
import org.ocpsoft.rewrite.test.RewriteTest;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
@RunWith(Arquillian.class)
public class JoinChainingConfigurationTest extends RewriteTest
{
   @Deployment(testable = false)
   public static WebArchive getDeployment()
   {
      WebArchive deployment = RewriteTest
               .getDeployment()
               .addPackages(true, ConfigRoot.class.getPackage())
               .addAsServiceProvider(ConfigurationProvider.class, JoinChainingConfigurationProvider.class);
      return deployment;
   }

   @Test
   public void testJoinWithChaining() throws Exception
   {
      HttpAction action = get("/chain");
      Assert.assertEquals(201, action.getStatusCode());
      Assert.assertEquals("/chain", action.getCurrentContextRelativeURL());
   }

   @Test
   public void testJoinChainingFromInternalServletForward() throws Exception
   {
      HttpAction action = get("/chain-from-servlet");
      Assert.assertEquals(201, action.getStatusCode());
      Assert.assertEquals("/chain-from-servlet", action.getCurrentContextRelativeURL());
   }

   @Test
   public void testJoinWithoutChaining() throws Exception
   {
      HttpAction action = get("/nochain");
      Assert.assertEquals(404, action.getStatusCode());
      Assert.assertEquals("/nochain", action.getCurrentContextRelativeURL());
      Assert.assertEquals("true", action.getResponseHeaderValues("No-Chain").get(0));
   }

   @Test
   public void testMultipleJoinsWithoutChaining() throws Exception
   {
      HttpAction action = get("/nochain-many");
      Assert.assertEquals(404, action.getStatusCode());
      Assert.assertEquals("/nochain-many", action.getCurrentContextRelativeURL());
      Assert.assertTrue(action.getResponseHeaderValues("No-Chain").isEmpty());
   }

   @Test
   public void testJoinWithChainingToWithoutChaining() throws Exception
   {
      HttpAction action = get("/chain-nochain");
      Assert.assertEquals(404, action.getStatusCode());
      Assert.assertEquals("/chain-nochain", action.getCurrentContextRelativeURL());
      Assert.assertEquals("true", action.getResponseHeaderValues("No-Chain").get(0));
   }
}