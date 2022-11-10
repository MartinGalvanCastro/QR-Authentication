package com.example.qrcode_auth.web;

import com.example.qrcode_auth.web.dto.ws.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.qrcode_auth.config.ConfigConstant.HANDSHAKE;
import static com.example.qrcode_auth.config.ConfigConstant.QRTOPIC;

@Controller
@CrossOrigin(origins = "*")
public class WSController {

    private final SimpMessagingTemplate messagingTemplate;


    public WSController(
            SimpMessagingTemplate messagingTemplate){
        this.messagingTemplate = messagingTemplate;
    }


    //Mapped to /app/hello
    @MessageMapping("/hello")
    //Returns to 'all' topic, messages
    @SendTo("/all/messages")
    public String send(Message message, @Header("simpSessionId") String sessionId) throws Exception{
        Logger.getAnonymousLogger().log(Level.SEVERE,message.toString());
        Logger.getAnonymousLogger().log(Level.SEVERE,"SESSION ID: " + sessionId);
        return sessionId;
    }

    @SubscribeMapping(HANDSHAKE)
    @SendToUser
    public void sendSpecific(Message msg, @Header("simpSessionId") String sessionId) throws Exception {
        Logger.getAnonymousLogger().log(Level.SEVERE,"Session ID: "+sessionId);
        messagingTemplate.convertAndSendToUser(msg.getTo(), QRTOPIC, sessionId);
    }

}
