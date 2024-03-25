package com.hobos.freeter.auth;

import com.hobos.freeter.member.LoginDTO;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController()
@RequestMapping("/auth")
public class AuthController {
    @PostMapping("/login")
    public String login(@RequestBody LoginDTO dto) {
        return "login";
    }

    @GetMapping("test")
    public String test(Principal user) {

        System.out.println("@@@@@@@@@@@@@@@@@" + user);

        return "test";
    }

    @PostMapping("/logout")
    public String logout() {
        return "logout";
    }

}
