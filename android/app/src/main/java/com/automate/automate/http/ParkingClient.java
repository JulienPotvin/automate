package com.automate.automate.http;

import com.automate.automate.Constants;
import com.automate.automate.exceptions.AutomateException;
import com.automate.automate.exceptions.AutomateNetworkException;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by davidfrancoeur on 2015-11-14.
 */
public class ParkingClient {
    public final static String AUTOMATE_PARKING = Constants.API_URL + "/parkings";

    private final static String PARAMS_QUERY = "query";

    private final OkHttpClient client;

    public ParkingClient(OkHttpClient client) {
        this.client = client;
    }

    public String execute(Request request) throws AutomateException {
        try {
            Response response = client.newCall(request).execute();
            return response.toString();
        } catch (IOException ex) {
            throw new AutomateNetworkException(request, ex);
        }
    }

    public static Request.Builder builder() {
        return new Request.Builder();
    }

    public static HttpUrl parkingUrl(String query, Double latitude, Double longitude){
        return new HttpUrl.Builder()
                .addQueryParameter(PARAMS_QUERY, query)
                .build();
    }
}
