package be.technobel.fbrassine.labobackend.service;


import be.technobel.fbrassine.labobackend.models.dto.UserDTO;
import be.technobel.fbrassine.labobackend.models.form.CreateAccountForm;
import be.technobel.fbrassine.labobackend.models.form.ResetPasswordForm;

import java.util.List;

public interface UserService {

    boolean checkEmailNotTaken(String email);
    void createAcount(CreateAccountForm form);
    List<UserDTO> getAll();
    UserDTO getOne(String login);
    void resetPassword(Long id);
    void changePassword(String login, ResetPasswordForm form);
    void disabled(Long id);
}
