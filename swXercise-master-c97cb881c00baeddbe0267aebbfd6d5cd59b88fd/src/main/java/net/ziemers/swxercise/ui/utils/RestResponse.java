package net.ziemers.swxercise.ui.utils;

import net.ziemers.swxercise.ui.enums.ResponseState;

public class RestResponse {

    private ResponseState responseState;

    private String message;

    public RestResponse() {
        this(ResponseState.SUCCESS);
    }

    public RestResponse(final ResponseState responseState) {
        this.responseState = responseState;        
    }

    public RestResponse(final ResponseState responseState, final String message) {
        this(responseState);
        this.message = message;
    }

    public RestResponse(final String message) {
        this();
        this.message = message;
    }

    public int getResponseCode() {
        return responseState.getResponseCode();
    }

    public String getResponseText() {
        return responseState.getResponseText();
    }

    public String getMessage() { return message; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RestResponse that = (RestResponse) o;

        return responseState == that.responseState;
    }

}

