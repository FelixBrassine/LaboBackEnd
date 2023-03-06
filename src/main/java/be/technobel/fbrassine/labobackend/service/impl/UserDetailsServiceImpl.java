package be.technobel.fbrassine.labobackend.service.impl;

import be.technobel.fbrassine.labobackend.exceptions.ResourceNotFoundException;
import be.technobel.fbrassine.labobackend.models.entity.User;
import be.technobel.fbrassine.labobackend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository.findByLogin(login)
                .orElseThrow( () -> new ResourceNotFoundException(User.class, login));
    }

}
