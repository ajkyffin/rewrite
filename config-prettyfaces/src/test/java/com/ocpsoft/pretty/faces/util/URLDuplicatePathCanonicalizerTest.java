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
package com.ocpsoft.pretty.faces.util;

import org.junit.Test;

import com.ocpsoft.pretty.faces.util.URLDuplicatePathCanonicalizer;

import static org.assertj.core.api.Assertions.assertThat;

public class URLDuplicatePathCanonicalizerTest
{
    URLDuplicatePathCanonicalizer c = new URLDuplicatePathCanonicalizer();

    @Test
    public void testRemovesDuplicatePaths() throws Exception
    {
        String url = "http://ocpsoft.com/prettyfaces/../prettyfaces/";

        String expected = "http://ocpsoft.com/prettyfaces/";

        assertThat(c.canonicalize(url)).isEqualTo(expected);
    }

    @Test
    public void testIgnoresRelativePaths() throws Exception
    {
        String url = "http://ocpsoft.com/prettyfaces/../scrumshark/";

        String expected = "http://ocpsoft.com/prettyfaces/../scrumshark/";

        assertThat(c.canonicalize(url)).isEqualTo(expected);
    }
}
