package me.ziok.application.controller;

import me.ziok.application.payload.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/oauth2")
public class Oauth2Controller {

    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    public ResponseEntity<?> receive(@RequestParam("token") String token ) {
        System.out.println("ttttttttttt");
        System.out.println(token);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
