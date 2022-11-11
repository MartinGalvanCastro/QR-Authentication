package com.example.qrcode_auth.web;

import com.example.qrcode_auth.model.App_User;
import com.example.qrcode_auth.service.UserService;
import com.example.qrcode_auth.web.dto.CreateUserDTO;
import com.example.qrcode_auth.web.dto.LoginDTO;
import com.example.qrcode_auth.web.dto.MessageDTO;
import com.example.qrcode_auth.web.dto.QRLoginDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.ExecutionException;
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

    @PostMapping("/qr_login")
    @SendToUser
    public ResponseEntity<MessageDTO> qrLogin(@RequestBody QRLoginDTO qrLoginDTO, HttpServletRequest HttpServletRequest){

        //Base URL
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(HttpServletRequest)
                .replacePath(null)
                .build()
                .toUriString();

        Logger.getAnonymousLogger().log(Level.SEVERE, "BASE URL FOR WS CONENECTION");

        //Stomp WS Creation
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        SockJsClient sockJsClient = new SockJsClient(List.of(new WebSocketTransport(webSocketClient)));
        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        //Session Creation
        StompSession session;
        try {
            session = stompClient
                    .connect(
                            baseUrl+"/ws",
                            new StompSessionHandlerAdapter() {})
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(new MessageDTO("auth token:{"+qrLoginDTO.getAuthToken()+"} sent to WS Session:{"+qrLoginDTO.getWSSessionID()+"}"),HttpStatus.OK);
    }

}
