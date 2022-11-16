package com.example.qrcode_auth.web;

import com.example.qrcode_auth.service.QRService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;

@RestController
@RequestMapping("/api")
public class QRController {

    private final QRService qrService;

    public QRController(
            QRService qrService
    ) {
        this.qrService = qrService;
    }


    @GetMapping(
            path = "/qr_sample",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public BufferedImage getSampleQR() {
        return qrService.encodeString("google.com");
    }
}

