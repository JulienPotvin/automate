package com.automate.automate.exceptions;

/**
 * Created by davidfrancoeur on 2015-11-14.
 */
public class AutomateException extends Exception {
    public AutomateException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
