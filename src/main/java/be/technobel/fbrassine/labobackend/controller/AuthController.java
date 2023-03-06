package be.technobel.fbrassine.labobackend.controller;

import be.technobel.fbrassine.labobackend.models.dto.AuthDTO;
import be.technobel.fbrassine.labobackend.models.form.LoginForm;
import be.technobel.fbrassine.labobackend.models.form.RegisterRequestForm;
import be.technobel.fbrassine.labobackend.service.AuthService;
import be.technobel.fbrassine.labobackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public AuthDTO login(@RequestBody @Valid LoginForm form){
        return authService.login(form);
    }
    @PostMapping("/register")
    public void register(@RequestBody @Valid RegisterRequestForm form){
        authService.register(form);
    }
}
