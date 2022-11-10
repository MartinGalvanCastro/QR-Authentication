package com.example.qrcode_auth.web.dto.ws;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message {

    private String to;
    private String text;

}