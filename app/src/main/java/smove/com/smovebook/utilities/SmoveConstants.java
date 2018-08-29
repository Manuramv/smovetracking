package smove.com.smovebook.utilities;

import android.support.v4.app.NavUtils;

import retrofit2.Response;
import smove.com.smovebook.networking.response.bookingapi.GetBookingAvailabilityResponse;
import smove.com.smovebook.networking.response.carlocation.GetCarLocationResponse;

/**
 * Created by Manuramv on 8/28/2018.
 */

public class SmoveConstants {
    public static  Response<GetCarLocationResponse> CAR_LOCATION = null;
    public static Response<GetBookingAvailabilityResponse> BOOK_RESPONSE = null;
    public static  String END_POINT = "https://challenge.smove.sg/";
}
