package com.example.yedocb.user.login;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserLoginController {

    private final UserLoginService userLoginService;

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam String uId,
                                            @RequestParam String uPwd) {
        boolean success = userLoginService.login(uId, uPwd);
        return success ? ResponseEntity.ok("Login success")
                       : ResponseEntity.status(401).body("Login failed");
    }
}