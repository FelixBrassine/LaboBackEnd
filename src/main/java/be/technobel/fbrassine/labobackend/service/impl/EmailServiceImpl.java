package be.technobel.fbrassine.labobackend.service.impl;

import be.technobel.fbrassine.labobackend.models.entity.User;
import be.technobel.fbrassine.labobackend.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final MailSender mailSender;
    private final JavaMailSender emailSender;

    public EmailServiceImpl(MailSender mailSender, JavaMailSender emailSender) {
        this.mailSender = mailSender;
        this.emailSender = emailSender;
    }

    @Override
    public void sendResetPassword(User user) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(user.getEmail());
        msg.setFrom("noreply@labobackend.com");
        msg.setText(
                "Dear " + user.getFirstName()
                        + ", "
                        + user.getName()
                        + ", your password has been reset. " +
                        "Your new password is : \"ChangeMe\"."
        );
        msg.setSubject("Password reset");
        try{
            mailSender.send(msg);
        }
        catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void sendRequestInfo(User user, String subject, String text) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(user.getEmail());
        msg.setFrom("noreply@labobackend.com");
        msg.setText(text);
        msg.setSubject(subject);
        try{
            mailSender.send(msg);
        }
        catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
