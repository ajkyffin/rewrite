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
package org.ocpsoft.rewrite.config.tuckey;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.ocpsoft.rewrite.config.ConfigurationProvider;
import org.ocpsoft.rewrite.test.HttpAction;
import org.ocpsoft.rewrite.test.RewriteTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
@RunWith(Arquillian.class)
public class TuckeyConfigurationProviderTest extends RewriteTest
{
   @Deployment(testable = false)
   public static WebArchive getDeployment()
   {
      WebArchive deployment = RewriteTest.getDeployment()
               .addAsLibraries(resolveDependencies("org.tuckey:urlrewritefilter:3.1.0"))
               .addAsServiceProvider(ConfigurationProvider.class, TuckeyConfigurationProvider.class)
               .addAsWebInfResource("urlrewrite.xml")
               .addAsWebResource(new StringAsset("exists"), "index.html")
               .addPackages(true, TuckeyRoot.class.getPackage());

      return deployment;
   }

   @Test
   public void testConfigurationIntegratesWithRedirectFlow() throws Exception
   {
      HttpAction action = get("/some/olddir/value");
      assertThat(action.getCurrentURL()).isEqualTo("/very/newdir/value");
      assertThat(action.getStatusCode()).isEqualTo(404);
   }

   @Test
   public void testConfigurationIntegratesWithForwardFlow() throws Exception
   {
      HttpAction action = get("/some/fordir/value");
      assertThat(action.getCurrentURL()).isEqualTo("/very/newdir/value");
      assertThat(action.getStatusCode()).isEqualTo(404);
   }

   @Test
   public void testConfigurationIntegratesWithForwardFlowNonRedirecting() throws Exception
   {
      HttpAction action = get("/some/fordir/nonredirect");
      assertThat(action.getCurrentContextRelativeURL()).isEqualTo("/some/fordir/nonredirect");
      assertThat(action.getStatusCode()).isEqualTo(200);
   }

   @Test
   public void testConfigurationIntegratesWithForwardFlowNonRedirecting404() throws Exception
   {
      HttpAction action = get("/some/404/dir");
      assertThat(action.getCurrentContextRelativeURL()).isEqualTo("/some/404/dir");
      assertThat(action.getStatusCode()).isEqualTo(404);
   }

}
