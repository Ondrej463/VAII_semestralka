package com.vaii_semestralka.controlers;

import lombok.Getter;
import lombok.Setter;

public class RestReponse {
    public static final String NOT_FOUND = "Not found";
    public static final String OK = "Ok";

    @Getter @Setter
    private String responseStatus;
    @Getter @Setter
    private Object response;

    public RestReponse() {

    }
    public RestReponse(String responseStatus, Object response) {
        this.responseStatus = responseStatus;
        this.response = response;
    }
    public RestReponse(String responseStatus) {
        this.responseStatus = responseStatus;
    }
}
