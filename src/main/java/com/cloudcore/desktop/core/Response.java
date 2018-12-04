package com.cloudcore.desktop.core;

@SuppressWarnings({"ALL", "unused"})
public class Response {


    public String fullRequest;
    public String fullResponse;
    public boolean success;
    public String outcome;
    public int milliseconds;

    public Response() {
        this.success = false;
        this.outcome = "not used";
        this.milliseconds = 0;
        this.fullRequest = "No request";
        this.fullResponse = "No response";
    }
}

