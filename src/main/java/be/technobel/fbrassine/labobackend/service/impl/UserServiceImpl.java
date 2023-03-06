package be.technobel.fbrassine.labobackend.service.impl;

import be.technobel.fbrassine.labobackend.exceptions.ResourceNotFoundException;
import be.technobel.fbrassine.labobackend.models.dto.UserDTO;
import be.technobel.fbrassine.labobackend.models.entity.User;
import be.technobel.fbrassine.labobackend.models.entity.UserRole;
import be.technobel.fbrassine.labobackend.models.form.CreateAccountForm;
import be.technobel.fbrassine.labobackend.models.form.ResetPasswordForm;
import be.technobel.fbrassine.labobackend.repository.UserRepository;
import be.technobel.fbrassine.labobackend.service.EmailService;
import be.technobel.fbrassine.labobackend.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final EmailService emailService;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder encoder,
                           EmailService emailService){
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.emailService = emailService;
    }

    @Override
    public boolean checkEmailNotTaken(String email){
        return !userRepository.existsByEmail(email);
    }
    @Override
    public void createAcount(CreateAccountForm form) {
        User user = new User();
        user.setEmail(form.getEmail());
        user.setPassword(encoder.encode(form.getPassword()));
        user.setFirstName(form.getFirstName());
        user.setName(form.getName());
        user.setLogin(form.getLogin());
        user.setPhoneNumber(form.getPhoneNumber());
        user.setAdress(form.getAdress());
        user.setEnabled(form.isEnabled());
        user.setRoles(form.getRoles());

        userRepository.save(user);
    }

    @Override
    public List<UserDTO> getAll() {
        return userRepository.findAll().stream()
                .map(UserDTO::toDto)
                .toList();
    }

    @Override
    public UserDTO getOne(String login) {
        return userRepository.findByLogin(login)
                .map(UserDTO::toDto)
                .orElseThrow( () -> new UsernameNotFoundException("couldn't find user with login " + login) );
    }

    @Override
    public void resetPassword(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException(User.class, id) );
        emailService.sendResetPassword(user);
        user.setPassword(encoder.encode("ChangeMe"));
        userRepository.save(user);
    }

    @Override
    public void changePassword(String login, ResetPasswordForm form) {
        User user = userRepository.findByLogin(login)
                .orElseThrow( () -> new ResourceNotFoundException(User.class, login));
        user.setPassword(encoder.encode(form.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void disabled(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException(User.class, id) );
        user.setEnabled(false);
        userRepository.save(user);
    }
}
