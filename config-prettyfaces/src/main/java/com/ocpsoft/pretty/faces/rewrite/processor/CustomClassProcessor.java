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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.ocpsoft.pretty.PrettyException;
import com.ocpsoft.pretty.faces.config.rewrite.RewriteRule;
import com.ocpsoft.pretty.faces.rewrite.Processor;

/**
 * @author Lincoln Baxter, III <lincoln@ocpsoft.com>
 */
public class CustomClassProcessor implements Processor
{

   public String processInbound(final HttpServletRequest request, final HttpServletResponse response,
            final RewriteRule rule,
            final String url)
   {
      String result = url;
      if (rule.getProcessor().length() > 0)
      {
         try
         {
            Class<?> processorClass = Class.forName(rule.getProcessor());
            Processor processor = (Processor) processorClass.newInstance();
            result = processor.processInbound(request, response, rule, url);
         }
         catch (Exception e)
         {
            throw new PrettyException("Error occurred exececuting processor of type: " + rule.getProcessor()
                        + ", for input URL <[" + url + "]>", e);
         }
      }
      return result;
   }

   public String processOutbound(final HttpServletRequest request, final HttpServletResponse response,
            final RewriteRule rule, final String url)
   {
      String result = url;
      if (rule.getProcessor().length() > 0)
      {
         try
         {
            Class<?> processorClass = Class.forName(rule.getProcessor());
            Processor processor = (Processor) processorClass.newInstance();
            result = processor.processOutbound(request, response, rule, url);
         }
         catch (Exception e)
         {
            throw new PrettyException("Error occurred exececuting processor of type: " + rule.getProcessor()
                        + ", for input URL <[" + url + "]>", e);
         }
      }
      return result;
   }

}
