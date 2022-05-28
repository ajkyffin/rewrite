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
package org.ocpsoft.rewrite.prettyfaces;

import jakarta.faces.context.FacesContext;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletRequest;

import org.ocpsoft.rewrite.faces.spi.FacesActionUrlProvider;

import com.ocpsoft.pretty.PrettyContext;
import com.ocpsoft.pretty.faces.beans.ExtractedValuesURLBuilder;
import com.ocpsoft.pretty.faces.config.mapping.UrlMapping;

/**
 * @author Lincoln Baxter, III <lincoln@ocpsoft.com>
 */
public class PrettyFacesActionUrlProvider implements FacesActionUrlProvider
{

   @Override
   public String getActionURL(FacesContext context, String viewId)
   {
      HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

      DispatcherType type = request.getDispatcherType();
      if (type == null || type != DispatcherType.ERROR) {
         return _getActionURL(context, viewId);
      }

      return null;
   }

   public String _getActionURL(final FacesContext context, final String viewId)
   {
      PrettyContext prettyContext = PrettyContext.getCurrentInstance(context);
      String result = null;
      if (prettyContext.isPrettyRequest() && (viewId != null)
               && viewId.equals(context.getViewRoot().getViewId()))
      {
         ExtractedValuesURLBuilder builder = new ExtractedValuesURLBuilder();
         UrlMapping mapping = prettyContext.getCurrentMapping();
         result = prettyContext.getContextPath() + builder.buildURL(mapping) + builder.buildQueryString(mapping);
      }
      return result;
   }

   @Override
   public int priority()
   {
      return Integer.MIN_VALUE;
   }
}