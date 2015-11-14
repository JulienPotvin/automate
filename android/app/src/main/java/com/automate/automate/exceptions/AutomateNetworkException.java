package com.automate.automate.exceptions;

import com.squareup.okhttp.Request;

/**
 * Created by davidfrancoeur on 2015-11-14.
 */
public class AutomateNetworkException extends AutomateException {

    private static final String NETWORK_ERROR = "A network error occured with the following request %s";

    public AutomateNetworkException(Request failedRequest, Throwable throwable) {
        super(String.format(NETWORK_ERROR, failedRequest), throwable);
    }
}
