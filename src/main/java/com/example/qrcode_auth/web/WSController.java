package com.example.qrcode_auth.web;

import com.example.qrcode_auth.service.QRService;
import com.example.qrcode_auth.web.dto.QRLoginDTO;
import com.example.qrcode_auth.web.dto.ws.Message;
import com.example.qrcode_auth.web.dto.ws.QRMessage;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.qrcode_auth.config.ConfigConstant.HANDSHAKE;
import static com.example.qrcode_auth.config.ConfigConstant.QRTOPIC;

@Controller
@CrossOrigin(origins = "*")
public class WSController {

    private final QRService qrService;

    private final SimpMessagingTemplate template;

    public WSController(
            QRService qrService,
            SimpMessagingTemplate template){
        this.qrService = qrService;
        this.template = template;
    }


    //Mapped to /app/hello
    @MessageMapping("/hello")
    //Returns to 'all' topic, messages
    @SendTo("/all/messages")
    public String send(Message message, @Header("simpSessionId") String sessionId) {
        Logger.getAnonymousLogger().log(Level.INFO,message.toString());
        Logger.getAnonymousLogger().log(Level.INFO,"SESSION ID: " + sessionId);
        return sessionId;
    }

    @SubscribeMapping(HANDSHAKE)
    public String sendSessionID(@Header("simpSessionId") String sessionId) {
        Logger.getAnonymousLogger().log(Level.INFO,"SESSION ID: " + sessionId);
        return sessionId;
    }

    @SubscribeMapping(QRTOPIC)
    public QRMessage sendQRCode(@Header("simpSessionId") String sessionId) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Logger.getAnonymousLogger().log(Level.INFO,"SESSION ID: " + sessionId);
        BufferedImage WSSessionID = qrService.encodeString(sessionId);
        ImageIO.write(WSSessionID,"jpeg",baos);
        byte[] encoded = Base64.getEncoder().encode(baos.toByteArray());
        return new QRMessage(sessionId, new String(encoded, StandardCharsets.UTF_8));
    }


    @MessageMapping(QRTOPIC+"/login")
    public void QRLogin(QRLoginDTO qrLoginDTO){
        Logger.getAnonymousLogger().log(Level.INFO,qrLoginDTO.toString());
        Logger.getAnonymousLogger().log(Level.INFO,"DESTINATION: "+ QRTOPIC+"/login/"+qrLoginDTO.getWSSessionID());
        template.convertAndSend(QRTOPIC+"/login/"+qrLoginDTO.getWSSessionID(),qrLoginDTO.getAuthToken());
    }

}
