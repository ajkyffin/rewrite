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
package test.org.ocpsoft.rewrite.cdi.convert;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter("AdvancedStringConverter")
public class AdvancedStringConverterById implements Converter
{

   @Override
   public Object getAsObject(FacesContext context, UIComponent component, String value)
   {
      return new AdvancedString(value);
   }

   @Override
   public String getAsString(FacesContext context, UIComponent component, Object value)
   {
      throw new UnsupportedOperationException();
   }

}
