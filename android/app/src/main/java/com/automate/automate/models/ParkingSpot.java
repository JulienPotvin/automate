package com.automate.automate.models;

import android.location.LocationManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import com.automate.automate.exceptions.AutomateJsonParsingException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Builder;
import lombok.Value;

/**
 * Created by davidfrancoeur on 2015-11-14.
 */
@Value
@Builder
public class ParkingSpot implements Parcelable {
    private final static String LOGGER_TAG = ParkingSpot.class.getName();

    private final static String JSON_ID = "parkingId";
    private final static String JSON_PARKING_LOCATION = "parkingLocation";
    private final static String JSON_LATITUDE = "lat";
    private final static String JSON_LONGITUDE = "long";
    private final static String JSON_AVAILABILTY = "physicalAvailability";
    private final static String JSON_BASE_PRICE = "basePrice";
    private final static String JSON_DISCOUNT = "discount";

    private final String id;
    private final String discount;
    private final Double basePrice;
    private final Double latitude;
    private final Double longitude;
    private final Boolean availability;

    public static ParkingSpot fromPayload(JSONObject reader) throws AutomateJsonParsingException {
        try {
            String id = reader.getString(JSON_ID);
            JSONObject location = reader.getJSONObject(JSON_PARKING_LOCATION);
            Double latitude = location.getDouble(JSON_LATITUDE);
            Double longitude = location.getDouble(JSON_LONGITUDE);
            Boolean availability = reader.getBoolean(JSON_AVAILABILTY);
            String discount = reader.optString(JSON_DISCOUNT);
            Double price = reader.getDouble(JSON_BASE_PRICE);

            return builder()
                    .id(id)
                    .latitude(latitude)
                    .longitude(longitude)
                    .availability(availability)
                    .basePrice(price)
                    .discount(discount)
                    .build();

        } catch (JSONException e) {
            throw new AutomateJsonParsingException(reader.toString(), e);
        }
    }

    public static List<ParkingSpot> fromArrayPayload(String jsonArray) throws AutomateJsonParsingException {
        try {
            List<ParkingSpot> result = new ArrayList<>();
            Log.d(LOGGER_TAG, String.format("Parsing: %s", jsonArray));

            if (!TextUtils.isEmpty(jsonArray)) {
                JSONArray array = new JSONArray(jsonArray);
                for (int i = 0; i < array.length(); i++) {
                    result.add(fromPayload(array.getJSONObject(i)));
                }
            }

            return result;

        } catch (JSONException e) {
            throw new AutomateJsonParsingException(jsonArray, e);
        }
    }

    public static ParkingSpot fromParcel(Parcel in) {
        return ParkingSpot.builder()
                .id(in.readString())
                .latitude(in.readDouble())
                .longitude(in.readDouble())
                .availability(in.readByte() != 0)
                .discount(in.readString())
                .basePrice(in.readDouble())
                .build();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeByte((byte) (this.availability ? 1 : 0));
        dest.writeString(this.discount);
        dest.writeDouble(this.basePrice);
    }

    public static final Creator<ParkingSpot> CREATOR = new Creator<ParkingSpot>() {
        @Override
        public ParkingSpot createFromParcel(Parcel in) {
            return fromParcel(in);
        }

        @Override
        public ParkingSpot[] newArray(int size) {
            return new ParkingSpot[size];
        }
    };
}
