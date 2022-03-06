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
package org.ocpsoft.rewrite.transform.markup.impl;

import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.jruby.embed.ScriptingContainer;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.ocpsoft.rewrite.servlet.http.event.HttpServletRewrite;
import org.ocpsoft.rewrite.transform.markup.Textile;
import org.ocpsoft.rewrite.transform.markup.impl.JRubyTransformer;

import static org.assertj.core.api.Assertions.assertThat;

@Ignore
public class TextileTest
{
   private ServletContext context;
   private HttpServletRewrite event;

   @Before
   public void before()
   {
      context = Mockito.mock(ServletContext.class);
      Mockito.when(context.getAttribute(JRubyTransformer.CONTAINER_STORE_KEY))
               .thenReturn(new HashMap<Class<?>, ScriptingContainer>());

      event = Mockito.mock(HttpServletRewrite.class);
      Mockito.when(event.getServletContext()).thenReturn(context);
   }
   
   @After
   public void after()
   {
      new MarkupContextListener().contextDestroyed(new ServletContextEvent(context));
   }

   @Test
   public void testBoldText()
   {

      String textile = "This is *bold*!";
      String html = Textile.partialDocument().transform(event, textile);

      assertThat(html).isEqualTo("<p>This is <strong>bold</strong>!</p>");

   }

   @Test
   public void testHeaders()
   {

      String textile = "h1. Header\n\nh2. Section\n\nSome text!";
      String html = Textile.partialDocument().transform(event, textile);

      assertThat(normalize(html)).isEqualTo("<h1>Header</h1><h2>Section</h2><p>Some text!</p>");

   }

   @Test
   public void testBlockquote()
   {

      String textile = "bq. Some quote";
      String html = Textile.partialDocument().transform(event, textile);

      assertThat(normalize(html)).isEqualTo("<blockquote><p>Some quote</p></blockquote>");

   }

   @Test
   public void testLists()
   {

      String textile = "* One\n* Two";
      String html = Textile.partialDocument().transform(event, textile);

      assertThat(normalize(html).replaceAll(" ", "")).isEqualTo("<ul><li>One</li><li>Two</li></ul>");

   }

   @Test
   public void testCode()
   {

      String textile = "bc. private int n = 0;";
      String html = Textile.partialDocument().transform(event, textile);

      assertThat(normalize(html)).isEqualTo("<pre><code>private int n = 0;</code></pre>");

   }

   @Test
   public void testFullHtmlDocument()
   {

      String textile = "some text";
      String html = Textile.fullDocument().transform(event, textile);

      assertThat(html.contains("<!DOCTYPE html")).as("DOCTYPE is missing").isTrue();
      assertThat(html.contains("<html")).as("html tag is missing").isTrue();
      assertThat(html.contains("<body>")).as("body tag is missing").isTrue();
      assertThat(html.contains("<p>some text</p>")).as("Expected text missing").isTrue();

   }

   @Test
   public void shouldRenderTitleCorrectly()
   {

      String textile = "some text";
      String html = Textile.fullDocument().withTitle("My Title").transform(event, textile);

      assertThat(html).contains("<title>My Title</title>");

   }

   @Test
   public void shouldAddStylesheetCorrectly()
   {

      String textile = "some text";
      String html = Textile.fullDocument().addStylesheet("http://localhost/style.css").transform(event, textile);

      assertThat(html).contains("http://localhost/style.css");

   }

   private static String normalize(String s)
   {
      return s.replaceAll("\n", "").replaceAll("[\t ]+", " ").trim();
   }

}
