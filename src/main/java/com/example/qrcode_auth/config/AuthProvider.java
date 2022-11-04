package com.example.qrcode_auth.config;

import com.example.qrcode_auth.model.App_User;
import com.example.qrcode_auth.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class AuthProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthProvider(
            UserService userService,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws BadCredentialsException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        try{
            App_User user = userService.findByEmail(email);
            Logger.getAnonymousLogger().log(Level.SEVERE,user.getPassword());
            Logger.getAnonymousLogger().log(Level.SEVERE,password);
            if(passwordEncoder.matches(password,user.getPassword()))
                return new UsernamePasswordAuthenticationToken(email,password,new ArrayList<>());
            else {
                throw new BadCredentialsException("Username/Password does not match for user" + authentication.getPrincipal());
            }
        }catch (EntityNotFoundException e){
            throw new BadCredentialsException("Username not found" + authentication.getPrincipal());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
