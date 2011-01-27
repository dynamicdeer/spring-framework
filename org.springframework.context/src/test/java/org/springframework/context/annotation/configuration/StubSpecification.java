/*
 * Copyright 2002-2011 the original author or authors.
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

package org.springframework.context.annotation.configuration;

import org.springframework.context.ExecutorContext;
import org.springframework.context.FeatureSpecification;
import org.springframework.context.InvalidSpecificationException;
import org.springframework.context.SpecificationExecutor;

public class StubSpecification implements FeatureSpecification {

	private final Class<? extends SpecificationExecutor> excecutorType;

	public StubSpecification() {
		this(StubSpecificationExecutor.class);
	}

	public StubSpecification(Class<? extends SpecificationExecutor> excecutorType) {
		this.excecutorType = excecutorType;
	}

	public void validate() throws InvalidSpecificationException {
	}

	public Class<? extends SpecificationExecutor> getExecutorType() {
		return this.excecutorType;
	}

}

class StubSpecificationExecutor implements SpecificationExecutor {

	public void execute(FeatureSpecification spec, ExecutorContext executorContext) {
	}

}