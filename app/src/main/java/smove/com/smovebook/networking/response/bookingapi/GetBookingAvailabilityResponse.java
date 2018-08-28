package smove.com.smovebook.networking.response.bookingapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import smove.com.smovebook.networking.response.bookingapi.Datum;

/**
 * Created by Manuramv on 8/28/2018.
 */

public class GetBookingAvailabilityResponse {
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }
}
