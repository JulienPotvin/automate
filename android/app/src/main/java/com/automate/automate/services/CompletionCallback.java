package com.automate.automate.services;

import com.automate.automate.exceptions.AutomateException;

/**
 * Created by davidfrancoeur on 2015-11-14.
 */
public interface CompletionCallback<T> {
    void onCompletion(T result, AutomateException error);
}
