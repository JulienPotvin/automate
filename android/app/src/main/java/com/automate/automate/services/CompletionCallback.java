package com.automate.automate.services;

/**
 * Created by davidfrancoeur on 2015-11-14.
 */
public interface CompletionCallback<Result> {
    void onCompletion(AutomateResult<Result> result);
}
