package com.zerobase.stockdividendservice.controller;

import com.zerobase.stockdividendservice.model.Auth;
import com.zerobase.stockdividendservice.psersist.entity.MemberEntity;
import com.zerobase.stockdividendservice.security.TokenProvider;
import com.zerobase.stockdividendservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final MemberService memberService;
	private final TokenProvider tokenProvider;

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody Auth.SignUp request) {
		MemberEntity memberEntity = memberService.register(request);
		log.info("Member Registered");
		return ResponseEntity.ok(memberEntity);
	}

	@PostMapping("/signin")
	public ResponseEntity<?> signin(@RequestBody Auth.SignIn request) {
		MemberEntity m = memberService.authenticate(request);
		String token = tokenProvider.generateToken(m.getUsername(),
			m.getRoles());
		return ResponseEntity.ok(token);
	}
}
