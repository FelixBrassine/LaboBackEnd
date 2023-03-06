package be.technobel.fbrassine.labobackend.service;

import be.technobel.fbrassine.labobackend.models.dto.AuthDTO;
import be.technobel.fbrassine.labobackend.models.form.LoginForm;
import be.technobel.fbrassine.labobackend.models.form.RegisterRequestForm;

public interface AuthService {

    AuthDTO login(LoginForm form);
    void register(RegisterRequestForm form);
}
