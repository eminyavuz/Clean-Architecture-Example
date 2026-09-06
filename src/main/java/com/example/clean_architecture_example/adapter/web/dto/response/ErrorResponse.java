package com.example.clean_architecture_example.adapter.web.dto.response;

import java.time.Instant;

public class ErrorResponse {
    private  final  String  code;
    private  final String message;
    private final Instant timeStamp;
    private  final String path;

    public ErrorResponse(String  code, String message,Instant timeStamp,String path)
    {
        this.code=code;
        this.message=message;
        this.path=path;
        this.timeStamp=timeStamp;
    }

    public String  getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    public String getPath() {
        return path;
    }
}
