package com.automate.automate.exceptions;

/**
 * Created by davidfrancoeur on 2015-11-14.
 */
public class AutomateJsonParsingException extends AutomateException {

    private final static String JSON_ERROR = "A JSON parsing error occured with the following payload %s";

    public AutomateJsonParsingException(String payload, Throwable throwable) {
        super(String.format(JSON_ERROR, payload), throwable);
    }
}
