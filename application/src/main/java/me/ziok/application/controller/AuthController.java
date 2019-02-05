package me.ziok.application.controller;

import me.ziok.application.exceptions.BadRequestException;
import me.ziok.application.infra.RequestToAccount;
import me.ziok.application.model.Account;
import me.ziok.application.payload.*;
import me.ziok.application.security.TokenProvider;
import me.ziok.application.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    AccountService accountService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateAccount(@Valid @RequestBody LoginRequest loginRequest) {

        //todo: 인자를 이메일, 패스워드 두개 받는 함수를 만들고, 그 안에서 authenticate 호출하기.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerAccount(@Valid @RequestBody SignUpRequest signUpRequest) {

        if (!accountService.isAbleToRegister(signUpRequest.getEmail(), signUpRequest.getNickName())) {

            throw new BadRequestException("Imposible to sign up with the email or nickname");

        }

        Account requestedAccount = RequestToAccount.toAccount(signUpRequest);

        Account account = accountService.registerAccount(requestedAccount);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/api/users/{username}")
                .buildAndExpand(account.getEmail()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Account registered successfully"));

    }

}
