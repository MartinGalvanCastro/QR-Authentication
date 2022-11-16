package com.example.qrcode_auth.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class QRLoginDTO {

    private String authToken;

    private String WSSessionID;
}
