package be.technobel.fbrassine.labobackend.controller;

import be.technobel.fbrassine.labobackend.models.dto.UserDTO;
import be.technobel.fbrassine.labobackend.models.form.CreateAccountForm;
import be.technobel.fbrassine.labobackend.models.form.ResetPasswordForm;
import be.technobel.fbrassine.labobackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping({"", "/all"})
    public List<UserDTO> getAll(){
        return userService.getAll();
    }
    @GetMapping("/profil")
    public UserDTO getOne(Authentication authentication){
        return userService.getOne(authentication.getPrincipal().toString());
    }
    @GetMapping("/profil/pass")
    public void changePassword(
            @RequestBody @Valid ResetPasswordForm form,
            Authentication authentication
    ){
        userService.changePassword(authentication.getPrincipal().toString() ,form);
    }
    @PatchMapping("/{id:[0-9]+}")
    public void setDisabled(@PathVariable long id){userService.disabled(id);}
    @PatchMapping("/{id:[0-9]+}/reset")
    public void resetPassword(@PathVariable long id){userService.resetPassword(id);}
    @PostMapping("/add")
    public void createAccount(@RequestBody @Valid CreateAccountForm form){
        userService.createAcount(form);
    }
}
