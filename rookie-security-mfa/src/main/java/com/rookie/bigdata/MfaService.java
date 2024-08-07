/*
 * Copyright 2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rookie.bigdata;

import com.j256.twofactorauth.TimeBasedOneTimePasswordUtil;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;

@Service
public class MfaService {

	public boolean check(String hexKey, String code) {
		try {
			return TimeBasedOneTimePasswordUtil.validateCurrentNumberHex(hexKey, Integer.parseInt(code), 100000);
		}
		catch (GeneralSecurityException ex) {
			throw new IllegalArgumentException(ex);
		}
	}

}
