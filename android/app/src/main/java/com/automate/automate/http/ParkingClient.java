package com.automate.automate.http;

import com.automate.automate.exceptions.AutomateException;
import com.automate.automate.exceptions.AutomateNetworkException;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import static com.automate.automate.http.HttpUtils.urlBuilder;

/**
 * Created by davidfrancoeur on 2015-11-14.
 */
public class ParkingClient {

    public final static String CONSUMER = "consumer";
    public final static String NEARBY_PARKINGS = "ListNearbyParkings";
    public final static String QUERY_PARAM_LATITUDE = "userLatitude";
    public final static String QUERY_PARAM_LONGITUDE = "userLongitude";
    public final static String QUERY_PARAM_QUERY = "googleDestinationQuery";
    public final static String QUERY_PARAM_PARKING_DURATION = "expectedParkingDuration";


    private final static String PARAMS_QUERY = "findNearby";

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

    public static HttpUrl nearbyParkingUrl(String query, String parkingDuration,
                                           String latitude, String longitude){
        return urlBuilder()
                .addPathSegment(CONSUMER)
                .addPathSegment(NEARBY_PARKINGS)
                .addQueryParameter(QUERY_PARAM_QUERY, query)
                .addQueryParameter(QUERY_PARAM_LATITUDE, latitude)
                .addQueryParameter(QUERY_PARAM_LONGITUDE, longitude)
                .addQueryParameter(QUERY_PARAM_PARKING_DURATION, parkingDuration)
                .build();
    }
}
