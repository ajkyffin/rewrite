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
package com.ocpsoft.pretty.faces.component;

import jakarta.faces.component.UIComponentBase;

/**
 * @author Lincoln Baxter, III <lincoln@ocpsoft.com>
 */
public class UrlBuffer extends UIComponentBase
{
    public static final String COMPONENT_TYPE = "com.ocpsoft.pretty.UrlBuffer";
    public static final String RENDERER_TYPE = "com.ocpsoft.pretty.UrlBuffer";
    private static final String PRETTY_FACES_FAMILY = "PRETTY_FACES_FAMILY";

    public UrlBuffer()
    {
        super();
    }

    @Override
    public String getFamily()
    {
        return PRETTY_FACES_FAMILY;
    }
}
