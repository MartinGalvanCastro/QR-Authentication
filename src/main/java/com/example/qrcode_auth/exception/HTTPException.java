package com.example.qrcode_auth.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class HTTPException{

    private int status;

    private String message;

    private long timestamp;

}
