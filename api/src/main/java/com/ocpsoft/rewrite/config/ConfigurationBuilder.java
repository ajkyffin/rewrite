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
package com.ocpsoft.rewrite.config;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
public class ConfigurationBuilder implements Configuration
{
   private final List<Rule> rules = new ArrayList<Rule>();

   ConfigurationBuilder()
   {}

   @Override
   public List<Rule> getRules()
   {
      return rules;
   }

   public static ConfigurationBuilder begin()
   {
      return new ConfigurationBuilder();
   }

   public RuleBuilder addRule()
   {
      RuleBuilder rule = new RuleBuilder(this);
      rules.add(rule);
      return rule;
   }

   public RuleBuilder addRule(final Rule rule)
   {
      RuleBuilder builder = new RuleBuilder(this, rule);
      rules.add(builder);
      return builder;
   }
}