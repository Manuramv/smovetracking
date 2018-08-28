package smove.com.smovebook.utilities;

import retrofit2.Response;
import smove.com.smovebook.networking.response.bookingapi.GetBookingAvailabilityResponse;

/**
 * Created by Manuramv on 8/28/2018.
 */

public class SmoveConstants {
    public static Response<GetBookingAvailabilityResponse> BOOK_RESPONSE = null;
    public static  String END_POINT = "https://challenge.smove.sg/";
}
