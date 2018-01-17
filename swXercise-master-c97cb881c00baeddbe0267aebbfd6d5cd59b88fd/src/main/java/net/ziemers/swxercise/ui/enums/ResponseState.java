package net.ziemers.swxercise.ui.enums;

public enum ResponseState {

    SUCCESS(0, "Success"),
    FAILED(1, "Failed"),
    ALREADY_EXISTING(2, "Already existing"),
    ;

    private final int responseCode;

    private final String responseText;

    ResponseState(final int responseCode, final String responseText) {
        this.responseCode = responseCode;
        this.responseText = responseText;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseText() {
        return responseText;
    }

}

