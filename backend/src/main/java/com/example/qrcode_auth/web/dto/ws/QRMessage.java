package com.example.qrcode_auth.web.dto.ws;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class QRMessage {

    private String sessionID;

    private String byteArray;

}
