package be.technobel.fbrassine.labobackend.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthFilter) throws Exception {

        http.csrf().disable();

        http.httpBasic().disable();

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeHttpRequests(
            registry -> registry
                    .requestMatchers( request -> request.getRequestURI().length() > 500 ).denyAll()

                    //USER
                    .requestMatchers(HttpMethod.GET, "/user/profil").authenticated()
                    .requestMatchers(HttpMethod.GET, "/user/profil/pass").authenticated()
                    .requestMatchers(HttpMethod.GET,"/user/all").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PATCH,"/user/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET,"/user/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST,"/user/**").hasRole("ADMIN")

                    //REGISTER REQUEST
                    .requestMatchers(HttpMethod.GET,"/register/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PATCH,"/register/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT,"/register/**").hasRole("ADMIN")

                    //MATERIAL
                    .requestMatchers(HttpMethod.POST,"/material/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PATCH,"/material/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE,"/material/**").hasRole("ADMIN")

                    //ROOM
                    .requestMatchers(HttpMethod.POST,"/room/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT,"/room/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PATCH,"/room/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE,"/room/**").hasRole("ADMIN")

                    //ROOM REQUEST
                    .requestMatchers(HttpMethod.DELETE,"/request/**").authenticated()
                    .requestMatchers(HttpMethod.GET,"/request/**").authenticated()
                    .requestMatchers(HttpMethod.PATCH, "/request/**").hasRole("ADMIN")

                    .anyRequest().permitAll()

        );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
