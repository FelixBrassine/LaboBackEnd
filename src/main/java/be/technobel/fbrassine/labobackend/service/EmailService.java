package be.technobel.fbrassine.labobackend.service;

import be.technobel.fbrassine.labobackend.models.entity.User;

public interface EmailService {
    void sendResetPassword(User user);
    void sendRequestInfo(User user, String subject, String text);
}
