/*
 * Copyright 2020-2021 the original author or authors.
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

package com.rookie.bigdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

/**
 * A controller for the token resource.
 *
 * @author Josh Cummings
 */
@RestController
public class RSAKeyTokenController {

	@Autowired
	JwtEncoder encoder;

	/**
	 * http请求：http://localhost:8888/token
	 * basic auth: user:password
	 * 获取到token之后 可以进行 http://localhost:8888/hello
	 * Authorization=Bearer eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoidXNlciIsImV4cCI6MTcyMjQ1MTMyNywiaWF0IjoxNzIyNDE1MzI3LCJzY29wZSI6ImFwcCJ9.GXT2Ag1DMBOWtjdSD8E50ZxR6b791CeP3C3RTf0UZ3ISMoUepI5LsOKU2NEj4YdUJPG046yA0QxTyjjiscw4pBBG-xZ9lshjPgsIAbn-zpmRgumQER751Un075nMg9wdmJWo6NZdarwTnwcnOkyE3m-YTFYH0W9horNIly1DXo5ZaBYivm-kg7VUlI_ro5oAdzXBo0X-caBozAenSXofIkiHuKxb3kNER45evVHmGoq8lcXvy2dLwo4wLuBXetMj1f3hiTbjJ5kmfhSK3jW1P93l3RiIXEuMjetqWoxU6XnDwb5SK6TBTWbJRayaIP3AlGL8FnQc9mCs-sg_5p-ZjA
	 * @param authentication
	 * @return
	 */
	@PostMapping("/token")
	public String token(Authentication authentication) {
		Instant now = Instant.now();
		long expiry = 36000L;
		// @formatter:off
		String scope = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(" "));
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("self")
				.issuedAt(now)
				.expiresAt(now.plusSeconds(expiry))
				.subject(authentication.getName())
				.claim("scope", scope)
				.build();
		// @formatter:on
		return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}

}
