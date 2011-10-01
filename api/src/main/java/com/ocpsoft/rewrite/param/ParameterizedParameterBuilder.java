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
package com.ocpsoft.rewrite.param;

import com.ocpsoft.rewrite.bind.Binding;

/**
 * Used to build {@link Parameterized} instances.
 * 
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
public class ParameterizedParameterBuilder<T> implements Parameterized<ParameterizedParameterBuilder<T>, T>
{
   private final Parameterized<ParameterizedParameterBuilder<T>, T> parent;
   private final Parameter<String> parameter;

   public ParameterizedParameterBuilder(final Parameterized<ParameterizedParameterBuilder<T>, T> parent,
            final Parameter<String> parameter)
   {
      this.parent = parent;
      this.parameter = parameter;
   }

   /**
    * The {@link Parameter} must match the given pattern.
    */
   public ParameterizedParameterBuilder<T> matches(final String pattern)
   {
      parameter.matches(pattern);
      return this;
   }

   /**
    * The {@link Parameter} binds to the given {@link Binding}
    */
   public ParameterizedParameterBuilder<T> bindsTo(final Binding binding)
   {
      parameter.bindsTo(binding);
      return this;
   }

   @Override
   public ParameterizedParameterBuilder<T> where(final String param)
   {
      return parent.where(param);
   }

   @Override
   public ParameterizedParameterBuilder<T> where(final String param, final T pattern)
   {
      return parent.where(param, pattern);
   }

   @Override
   public ParameterizedParameterBuilder<T> where(final String param, final T pattern, final Binding binding)
   {
      return parent.where(param, pattern, binding);
   }

   @Override
   public ParameterizedParameterBuilder<T> where(final String param, final Binding binding)
   {
      return parent.where(param, binding);
   }
}