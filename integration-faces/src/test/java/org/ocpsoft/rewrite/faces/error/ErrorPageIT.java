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
package org.ocpsoft.rewrite.faces.error;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ocpsoft.rewrite.config.ConfigurationProvider;
import org.ocpsoft.rewrite.test.HttpAction;
import org.ocpsoft.rewrite.test.RewriteIT;
import org.ocpsoft.rewrite.test.RewriteITBase;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Arquillian.class)
public class ErrorPageIT extends RewriteITBase
{

   @Deployment(testable = false)
   public static WebArchive getDeployment()
   {
      // not using FacesBase.getDeployment() so we cat set a custom web.xml
      return RewriteIT.getDeploymentNoWebXml()
               .setWebXML("error-page-web.xml")
               .addAsWebInfResource("faces-config.xml", "faces-config.xml")
               .addAsWebInfResource(new StringAsset("<beans/>"), "beans.xml")
               .addClass(ErrorPageConfig.class)
               .addAsServiceProviderAndClasses(ConfigurationProvider.class, ErrorPageConfig.class)
               .addAsWebResource(EmptyAsset.INSTANCE, "some-page.xhtml")
               .addAsWebResource("error-page-404.xhtml", "404.xhtml");

   }

   @Test
   public void shouldRewriteOutboundLinksWithDirectAccess() throws Exception
   {
      HttpAction action = get("/404.xhtml");
      assertThat(action.getResponseContent()).contains("/rewritten");
   }

   @Test
   public void shouldRewriteOutboundLinksForErrorPage() throws Exception
   {
      HttpAction action = get("/does-not-exist");
      assertThat(action.getResponseContent()).contains("/rewritten");
   }

}