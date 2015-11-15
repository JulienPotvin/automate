package com.automate.automate.http;

import com.automate.automate.exceptions.AutomateException;
import com.automate.automate.exceptions.AutomateJsonParsingException;
import com.automate.automate.exceptions.AutomateNetworkException;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static com.automate.automate.http.HttpUtils.urlBuilder;

/**
 * Created by davidfrancoeur on 2015-11-14.
 */
public class ParkingClient {

    public final static String CONSUMER = "consumer";
    public final static String PARKING = "parking";
    public final static String LIST_STATES = "listStates";
    public final static String STATE = "state";
    public final static String NEARBY_PARKINGS = "listNearbyParkings";

    public final static String QUERY_PARAM_LATITUDE = "lat";
    public final static String QUERY_PARAM_LONGITUDE = "lon";

    public final static String QUERY_PARAM_QUERY = "googleDestinationQuery";
    public final static String QUERY_PARAM_PARKING_DURATION = "expectedParkingDuration";


    private final static String PARAMS_QUERY = "findNearby";

    private final OkHttpClient client;

    public ParkingClient(OkHttpClient client) {
        this.client = client;
    }

    public String executeForPayload(Request request) throws AutomateException {
        try {
            return responseToPayload(client.newCall(request).execute());
        } catch (IOException ex) {
            throw new AutomateNetworkException(request, ex);
        }
    }

    public boolean executeForBoolean(Request request) throws AutomateException {
        try {
            Response execute = client.newCall(request).execute();
            return execute.isSuccessful();
        }catch (IOException ex) {
            throw new AutomateNetworkException(request, ex);
        }
    }

    private static String responseToPayload(Response response) throws IOException {
        return response.body().string();
    }

    public static HttpUrl nearbyParkingUrl(String latitude, String longitude) {
        return urlBuilder()
                .addPathSegment(CONSUMER)
                .addPathSegment(NEARBY_PARKINGS)
                .addQueryParameter(QUERY_PARAM_LATITUDE, latitude)
                .addQueryParameter(QUERY_PARAM_LONGITUDE, longitude)
                .build();
    }

    public static HttpUrl parkingUrl() {
        return urlBuilder()
                .addPathSegment(PARKING)
                .addPathSegment(LIST_STATES)
                .build();
    }


    public static HttpUrl parkingState() {
        return urlBuilder()
                .addPathSegment(PARKING)
                .addPathSegment(STATE)
                .build();
    }

    public static String statePayload(String id, boolean state) throws AutomateJsonParsingException {

        JSONObject obj = new JSONObject();
        try {
            obj.put("parkingId", id);
            obj.put("state", state);
            return obj.toString();
        } catch (JSONException e) {
            throw new AutomateJsonParsingException(obj.toString(), e);
        }
    }

}
