package me.ziok.application.controller;

import me.ziok.application.model.Account;
import me.ziok.application.payload.ApiResponse;
import me.ziok.application.payload.AuthResponse;
import me.ziok.application.security.TokenProvider;
import me.ziok.application.service.SocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/oauth2")
public class Oauth2Controller {

    @Autowired
            //todo: social에는 facebook 뿐 아니라 kakao, google 등도 있어서 이렇게 해놨는데, 막상 상황에 따라 class를 갈아 끼우는 걸 어떻게 할 지 모르겠음. 수정해야 할 부분.
    SocialService socialService;

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/signin/facebook", method = RequestMethod.GET)
    public ResponseEntity<?> authenticateFacebookAccount(@RequestParam("token") String accessToken) {

        //facebook template으로 유저 정보를 가져옴
        Account account = socialService.translateAccessTokenToAccount(accessToken);

        account = socialService.saveAccount(account);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/api/users/"+account.getEmail())
                .buildAndExpand(account.getEmail()).toUri();

        System.out.println("location");
        System.out.println(location);
//
//        System.out.println("authentication not made yet");
//
//        //todo: authenticationService 같은 걸 만들어서 거기에서 처리
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        //todo: account의 email 말고 accountName을 새로 생성
//                        account.getEmail(),
//                        //todo: 현재 password가 null일 것임. 그에 따라 에러가 발생할 수 있음.
//                        "4430515s*"
//                )
//        );
//
//        System.out.println("authentication made");
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        String token = tokenProvider.generateToken(authentication);

        return ResponseEntity.created(location).body(new ApiResponse(true, "Account registered successfully"));



//        return ResponseEntity.ok(new AuthResponse(token));


        // 아직 우리쪽에 회원가입이 안돼있다면, 회원가입 시킴

        // 토큰 발급해줌.
    }


}
