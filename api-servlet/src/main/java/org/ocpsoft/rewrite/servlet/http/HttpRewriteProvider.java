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
package org.ocpsoft.rewrite.servlet.http;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.ocpsoft.rewrite.event.Rewrite;
import org.ocpsoft.rewrite.servlet.ServletRewriteProvider;
import org.ocpsoft.rewrite.servlet.http.event.HttpServletRewrite;

/**
 * A {@link org.ocpsoft.rewrite.spi.RewriteProvider} that only operates on {@link HttpServletRequest} and
 * {@link HttpServletResponse} request cycle types.
 * 
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
public abstract class HttpRewriteProvider extends ServletRewriteProvider<HttpServletRewrite>
{
   @Override
   public boolean handles(final Rewrite event)
   {
      return event instanceof HttpServletRewrite;
   }

   @Override
   public final void rewrite(Rewrite event)
   {
      if (event instanceof HttpServletRewrite)
         rewriteHttp((HttpServletRewrite) event);
   }

   /**
    * Handle the current {@link HttpServletRewrite} event.
    */
   public abstract void rewriteHttp(HttpServletRewrite event);
}
