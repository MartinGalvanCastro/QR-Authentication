package com.example.qrcode_auth.web;

import com.example.qrcode_auth.model.App_User;
import com.example.qrcode_auth.service.UserService;
import com.example.qrcode_auth.web.dto.CreateUserDTO;
import com.example.qrcode_auth.web.dto.LoginDTO;
import com.example.qrcode_auth.web.dto.MessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationManager authManager;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public AuthController(
            AuthenticationManager authenticationManager,
            UserService userService,
            PasswordEncoder passwordEncoder) {
        this.authManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/login")
    public ResponseEntity<MessageDTO> postLoginRequest(
            @Valid @RequestBody LoginDTO loginDTO){
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword())
        );
        App_User user = userService.findByEmail(loginDTO.getEmail());
        SecurityContextHolder.getContext().setAuthentication(auth);
        Logger.getAnonymousLogger().log(Level.SEVERE,SecurityContextHolder.getContext().toString());
        return new ResponseEntity<>(new MessageDTO("Welcome back "+ user.getName()),HttpStatus.OK);
    }

    @PostMapping("/singup")
    @Transactional
    public ResponseEntity<MessageDTO> signUp(@Valid @RequestBody CreateUserDTO createUserDTO){
        String pass = createUserDTO.getPassword();
        String encodedPass = passwordEncoder.encode(createUserDTO.getPassword());
        createUserDTO.setPassword(encodedPass);
        App_User user = userService.create(createUserDTO.toModel());
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(createUserDTO.getEmail(),pass)
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        return new ResponseEntity<>(new MessageDTO("Welcome " + user.getName()),HttpStatus.OK);
    }

}
