package be.technobel.fbrassine.labobackend.service.impl;

import be.technobel.fbrassine.labobackend.exceptions.ResourceNotFoundException;
import be.technobel.fbrassine.labobackend.models.dto.AuthDTO;
import be.technobel.fbrassine.labobackend.models.entity.*;
import be.technobel.fbrassine.labobackend.models.form.LoginForm;
import be.technobel.fbrassine.labobackend.models.form.RegisterRequestForm;
import be.technobel.fbrassine.labobackend.repository.RegisterRequestRepository;
import be.technobel.fbrassine.labobackend.repository.UserRepository;
import be.technobel.fbrassine.labobackend.service.AuthService;
import be.technobel.fbrassine.labobackend.utils.JwtProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authManager;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final RegisterRequestRepository requestRepository;
    private final PasswordEncoder encoder;

    public AuthServiceImpl(
            AuthenticationManager authManager,
            JwtProvider jwtProvider,
            UserRepository userRepository,
            RegisterRequestRepository requestRepository,
            PasswordEncoder encoder
    ) {
        this.authManager = authManager;
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
        this.encoder = encoder;
    }

    @Override
    public AuthDTO login(LoginForm form) {

        authManager.authenticate(new UsernamePasswordAuthenticationToken(form.getLogin(), form.getPassword()));

        User user = userRepository.findByLogin(form.getLogin())
                .orElseThrow( () -> new ResourceNotFoundException(User.class, form.getLogin()));

        String token = jwtProvider.generateToken(user.getLogin(), List.copyOf(user.getRoles()));

        return AuthDTO.builder()
                .token(token)
                .login(user.getLogin())
                .roles(user.getRoles())
                .build();
    }

    @Override
    public void register(RegisterRequestForm form) {
        RegisterRequest request = form.toEntity();
        Status status = new Status();
        status.setRegisterRequest(request);
        status.setStatus(RequestStatus.PENDING);
        request.setDate(LocalDateTime.now());
        request.getRegisterStatusHistory().add(status);
        requestRepository.save( request );
    }
}