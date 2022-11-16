package com.example.UserLoginAndRegistration.registration;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/registration")
@AllArgsConstructor
@Slf4j
public class UserRegistrationController {
    private RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody RegistrationRequest request) {
        log.info("registering");
        return registrationService.register(request);
    }

    @GetMapping(path = "/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}


