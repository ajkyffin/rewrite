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
package org.ocpsoft.rewrite.faces.annotation.navigate;

import jakarta.enterprise.context.RequestScoped;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.Parameter;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.navigate.Navigate;

import javax.inject.Named;

@Named
@RequestScoped
@Join(path = "/navigate", to = "/faces/navigate-encoding.xhtml")
public class NavigateEncodingBean
{

   @Deferred
   @Parameter("q")
   private String query;

   public Navigate redirectSimpleString()
   {
      return Navigate.to(NavigateEncodingBean.class)
               .with("q", "foo");
   }

   public Navigate redirectStringWithSpace()
   {
      return Navigate.to(NavigateEncodingBean.class)
               .with("q", "foo bar");
   }

   public Navigate redirectProblematicCharacters()
   {
      return Navigate.to(NavigateEncodingBean.class)
               .with("q", "foo=bar");
   }

   public String getQuery()
   {
      return query;
   }

   public void setQuery(String query)
   {
      this.query = query;
   }

}