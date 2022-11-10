package com.example.qrcode_auth.web;

import com.example.qrcode_auth.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class QRController {

    public QRController(
            ) {
    }



    @GetMapping(
            path = "/qr_sample",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public BufferedImage getSampleQR() {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix;
        try {
            bitMatrix = qrCodeWriter.encode("google.com", BarcodeFormat.QR_CODE, 250, 250);
        } catch (WriterException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "ERROR DURING QR GENERATION");
            throw new RuntimeException(e);
        }
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    @GetMapping(
            path = "qr_login",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public BufferedImage getSessionIDFromQR(
            ServletRequest request,
            HttpServletResponse response) throws AuthenticationException {

        ObjectMapper mapper = new ObjectMapper();
        HttpServletRequest req = (HttpServletRequest) request;
        Cookie[] allCookies = req.getCookies();
        Cookie session = null;
        if (allCookies != null) {
            session =
                    Arrays.stream(allCookies).filter(x -> x.getName().equals("JSESSIONID"))
                            .findFirst().orElseThrow(()->new AuthenticationException("No Session found"));
        }else{
            throw new AuthenticationException("No Session found");
        }


        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix;
        try {
            bitMatrix = qrCodeWriter.encode(mapper.writeValueAsString(session), BarcodeFormat.QR_CODE, 250, 250);
        } catch (WriterException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "ERROR DURING QR GENERATION");
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

}

