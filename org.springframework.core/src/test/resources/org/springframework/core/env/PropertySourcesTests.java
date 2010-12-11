/*
 * Copyright 2002-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.core.env;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

public class PropertySourcesTests {
	@Test
	public void test() {
		PropertySources sources = new PropertySources();
		sources.addLast(new MockPropertySource("b"));
		sources.addLast(new MockPropertySource("d"));
		sources.addLast(new MockPropertySource("f"));

		assertThat(sources.size(), equalTo(3));
		assertThat(sources.contains("a"), is(false));
		assertThat(sources.contains("b"), is(true));
		assertThat(sources.contains("c"), is(false));
		assertThat(sources.contains("d"), is(true));
		assertThat(sources.contains("e"), is(false));
		assertThat(sources.contains("f"), is(true));
		assertThat(sources.contains("g"), is(false));

		sources.addBefore("b", new MockPropertySource("a"));
		sources.addAfter("b", new MockPropertySource("c"));

		assertThat(sources.size(), equalTo(5));
		assertThat(sources.asList().indexOf(PropertySource.named("a")), is(0));
		assertThat(sources.asList().indexOf(PropertySource.named("b")), is(1));
		assertThat(sources.asList().indexOf(PropertySource.named("c")), is(2));
		assertThat(sources.asList().indexOf(PropertySource.named("d")), is(3));
		assertThat(sources.asList().indexOf(PropertySource.named("f")), is(4));

		sources.addBefore("f", new MockPropertySource("e"));
		sources.addAfter("f", new MockPropertySource("g"));

		assertThat(sources.size(), equalTo(7));
		assertThat(sources.asList().indexOf(PropertySource.named("a")), is(0));
		assertThat(sources.asList().indexOf(PropertySource.named("b")), is(1));
		assertThat(sources.asList().indexOf(PropertySource.named("c")), is(2));
		assertThat(sources.asList().indexOf(PropertySource.named("d")), is(3));
		assertThat(sources.asList().indexOf(PropertySource.named("e")), is(4));
		assertThat(sources.asList().indexOf(PropertySource.named("f")), is(5));
		assertThat(sources.asList().indexOf(PropertySource.named("g")), is(6));

		sources.addLast(new MockPropertySource("a"));
		assertThat(sources.size(), equalTo(7));
		assertThat(sources.asList().indexOf(PropertySource.named("b")), is(0));
		assertThat(sources.asList().indexOf(PropertySource.named("c")), is(1));
		assertThat(sources.asList().indexOf(PropertySource.named("d")), is(2));
		assertThat(sources.asList().indexOf(PropertySource.named("e")), is(3));
		assertThat(sources.asList().indexOf(PropertySource.named("f")), is(4));
		assertThat(sources.asList().indexOf(PropertySource.named("g")), is(5));
		assertThat(sources.asList().indexOf(PropertySource.named("a")), is(6));

		sources.addFirst(new MockPropertySource("a"));
		assertThat(sources.size(), equalTo(7));
		assertThat(sources.asList().indexOf(PropertySource.named("a")), is(0));
		assertThat(sources.asList().indexOf(PropertySource.named("b")), is(1));
		assertThat(sources.asList().indexOf(PropertySource.named("c")), is(2));
		assertThat(sources.asList().indexOf(PropertySource.named("d")), is(3));
		assertThat(sources.asList().indexOf(PropertySource.named("e")), is(4));
		assertThat(sources.asList().indexOf(PropertySource.named("f")), is(5));
		assertThat(sources.asList().indexOf(PropertySource.named("g")), is(6));

		assertEquals(sources.remove("a"), true);
		assertThat(sources.size(), equalTo(6));
		assertThat(sources.contains("a"), is(false));

		assertEquals(sources.remove("a"), false);
		assertThat(sources.size(), equalTo(6));

		String bogusPS = "bogus";
		try {
			sources.addAfter(bogusPS, new MockPropertySource("h"));
			fail("expected non-existent PropertySource exception");
		} catch (IllegalArgumentException ex) {
			assertThat(ex.getMessage(),
					equalTo(String.format(PropertySources.NON_EXISTENT_PROPERTY_SOURCE_MESSAGE, bogusPS)));
		}

		sources.addFirst(new MockPropertySource("a"));
		assertThat(sources.size(), equalTo(7));
		assertThat(sources.asList().indexOf(PropertySource.named("a")), is(0));
		assertThat(sources.asList().indexOf(PropertySource.named("b")), is(1));
		assertThat(sources.asList().indexOf(PropertySource.named("c")), is(2));

		sources.replace("a", new MockPropertySource("a-replaced"));
		assertThat(sources.size(), equalTo(7));
		assertThat(sources.asList().indexOf(PropertySource.named("a-replaced")), is(0));
		assertThat(sources.asList().indexOf(PropertySource.named("b")), is(1));
		assertThat(sources.asList().indexOf(PropertySource.named("c")), is(2));

		sources.replace("a-replaced", new MockPropertySource("a"));

		try {
			sources.replace(bogusPS, new MockPropertySource("bogus-replaced"));
			fail("expected non-existent PropertySource exception");
		} catch (IllegalArgumentException ex) {
			assertThat(ex.getMessage(),
					equalTo(String.format(PropertySources.NON_EXISTENT_PROPERTY_SOURCE_MESSAGE, bogusPS)));
		}

		try {
			sources.addBefore("b", new MockPropertySource("b"));
			fail("expected exception");
		} catch (IllegalArgumentException ex) {
			assertThat(ex.getMessage(),
					equalTo(String.format(PropertySources.ILLEGAL_RELATIVE_ADDITION_MESSAGE, "b")));
		}

		try {
			sources.addAfter("b", new MockPropertySource("b"));
			fail("expected exception");
		} catch (IllegalArgumentException ex) {
			assertThat(ex.getMessage(),
					equalTo(String.format(PropertySources.ILLEGAL_RELATIVE_ADDITION_MESSAGE, "b")));
		}
	}

}