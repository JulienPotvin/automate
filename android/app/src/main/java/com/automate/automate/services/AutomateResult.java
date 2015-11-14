package com.automate.automate.services;

import com.automate.automate.exceptions.AutomateException;

import lombok.Value;

/**
 * Created by davidfrancoeur on 2015-11-14.
 */
@Value
public class AutomateResult<Result> {
    private final Result result;
    private final AutomateException error;

    public boolean hasError() {
        return error != null;
    }
}
