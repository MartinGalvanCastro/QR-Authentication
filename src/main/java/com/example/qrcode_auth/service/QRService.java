package com.example.qrcode_auth.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class QRService {

    private static final QRCodeWriter QR_CODE_WRITER = new QRCodeWriter();

    public BufferedImage encodeString(String string){
        BitMatrix bitMatrix;
        try {
            bitMatrix = QR_CODE_WRITER.encode(string, BarcodeFormat.QR_CODE, 300, 300);
        } catch (WriterException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "ERROR DURING QR GENERATION");
            throw new RuntimeException(e);
        }
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
}
