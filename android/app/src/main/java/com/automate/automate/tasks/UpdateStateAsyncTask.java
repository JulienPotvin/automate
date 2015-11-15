package com.automate.automate.tasks;

import android.os.AsyncTask;

import com.automate.automate.services.AutomateResult;
import com.automate.automate.services.CompletionCallback;
import com.automate.automate.services.ParkingSpotsService;

/**
 * Created by davidfrancoeur on 2015-11-14.
 */
public class UpdateStateAsyncTask extends AsyncTask<Void, Void, AutomateResult<Boolean>> {

    private final String id;
    private final boolean state;
    private final CompletionCallback<Boolean> callback;
    private final ParkingSpotsService service;

    public UpdateStateAsyncTask(String id, boolean state,
                                ParkingSpotsService service,
                                CompletionCallback<Boolean> callback) {
        this.id = id;
        this.state = state;
        this.callback = callback;
        this.service = service;
    }

    @Override
    protected AutomateResult<Boolean> doInBackground(Void... params) {
        return service.updateAvailable(id, state);

    }

    @Override
    protected void onPostExecute(AutomateResult<Boolean> result) {
        super.onPostExecute(result);

        callback.onCompletion(result);

    }

}
