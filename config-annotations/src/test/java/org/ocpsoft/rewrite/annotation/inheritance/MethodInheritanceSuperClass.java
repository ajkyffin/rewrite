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
package org.ocpsoft.rewrite.annotation.inheritance;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import org.ocpsoft.rewrite.annotation.RequestAction;

@Named
@RequestScoped
public class MethodInheritanceSuperClass
{

   private final StringBuilder logEntries = new StringBuilder();

   @RequestAction
   public void action2()
   {
      addEntry("action2 invoked");
   }

   protected void addEntry(String s)
   {
      logEntries.append("[").append(s).append("]\n");
   }

   public String getLogEntries()
   {
      return logEntries.toString();
   }

}
