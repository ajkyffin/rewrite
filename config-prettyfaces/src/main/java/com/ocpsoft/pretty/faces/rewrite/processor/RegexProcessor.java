/*
 * Copyright 2010 Lincoln Baxter, III
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

package com.ocpsoft.pretty.faces.rewrite.processor;

import java.util.regex.Matcher;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.ocpsoft.pretty.faces.config.rewrite.RewriteRule;
import com.ocpsoft.pretty.faces.rewrite.Processor;

/**
 * @author Lincoln Baxter, III <lincoln@ocpsoft.com>
 */
public class RegexProcessor implements Processor
{

   private String process(final HttpServletRequest request, final HttpServletResponse response, final RewriteRule rule,
            final String url)
   {
      if ((url == null) || (rule.getMatch().length() == 0) || (rule.getSubstitute().length() == 0))
      {
         return url;
      }

      Matcher m = rule.getPattern().matcher(url);
      StringBuffer result = new StringBuffer();
      while (m.find())
      {
         m.appendReplacement(result, rule.getSubstitute());
      }
      m.appendTail(result);
      return result.toString();
   }

   public String processInbound(final HttpServletRequest request, final HttpServletResponse response,
            final RewriteRule rule,
            final String url)
   {
      return process(request, response, rule, url);
   }

   public String processOutbound(final HttpServletRequest request, final HttpServletResponse response,
            final RewriteRule rule, final String url)
   {
      return process(request, response, rule, url);
   }

}
