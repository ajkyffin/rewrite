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
package com.ocpsoft.pretty.faces.spi;

import jakarta.servlet.ServletContext;

import com.ocpsoft.pretty.faces.config.PrettyConfig;

/**
 * Defines the interface to be used in performing post-config-parse step
 * processing.
 * 
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
public interface ConfigurationPostProcessor
{

   /**
    * Process the given PrettyConfig, returning the modified configuration.
    * 
    * @param servletContext The {@link ServletContext} for the application being
    *           configured.
    * @param config The {@link PrettyConfig} to process
    * @return The processed configuration
    */
   PrettyConfig processConfiguration(ServletContext context, PrettyConfig config);

}
