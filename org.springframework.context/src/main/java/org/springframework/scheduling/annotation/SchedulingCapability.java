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

package org.springframework.scheduling.annotation;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ContainerCapability;
import org.springframework.core.type.AnnotationMetadata;

public class SchedulingCapability implements ContainerCapability {

	public void enable(BeanDefinitionRegistry registry, AnnotationMetadata annotationMetadata) {
		if (registry.containsBeanDefinition(ScheduledAnnotationBeanPostProcessor.DEFAULT_BEAN_NAME)) {
			throw new IllegalStateException(
					"Only one ScheduledAnnotationBeanPostProcessor may exist within the context. " +
					"Did you declare @EnableScheduling more than once?");
		}

		registry.registerBeanDefinition(
				ScheduledAnnotationBeanPostProcessor.DEFAULT_BEAN_NAME,
				new RootBeanDefinition(ScheduledAnnotationBeanPostProcessor.class));
	}

}