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

package org.springframework.cache.interceptor;

import java.lang.reflect.Method;

/**
 * Interface used by CacheInterceptor. Implementations know
 * how to source cache operation attributes, whether from configuration,
 * metadata attributes at source level, or anywhere else.
 *
 * @author Costin Leau
 * @since 3.1
 */
public interface CacheOperationSource {

	/**
	 * Return the cache operation definition for this method,
	 * or <code>null</code> if the method is not cacheable.
	 * @param method the method to introspect
	 * @param targetClass the target class (may be <code>null</code>,
	 * in which case the declaring class of the method must be used)
	 * @return {@link CacheOperation} the matching cache operation,
	 * or <code>null</code> if none found
	 */
	CacheOperation getCacheOperation(Method method, Class<?> targetClass);

}
