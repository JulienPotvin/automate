package com.automate.automate.http;

import com.automate.automate.Constants;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Request;

import static com.automate.automate.Constants.HOST;
import static com.automate.automate.Constants.HTTP;
import static com.automate.automate.Constants.PORT;

/**
 * Created by davidfrancoeur on 2015-11-14.
 */
public class HttpUtils {

    public static Request.Builder requestBuilder() {
        return new Request.Builder();
    }
    public static HttpUrl.Builder urlBuilder() {
        return new HttpUrl.Builder()
                .scheme(HTTP)
                .host(HOST)
                .port(PORT);
    }

}
