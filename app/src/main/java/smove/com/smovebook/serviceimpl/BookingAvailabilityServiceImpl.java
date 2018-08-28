package smove.com.smovebook.serviceimpl;

import android.content.Context;
import android.net.Uri;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smove.com.smovebook.networking.ApiClientRequest;
import smove.com.smovebook.networking.response.bookingapi.GetBookingAvailabilityResponse;
import smove.com.smovebook.utilities.CommonUtils;
import smove.com.smovebook.utilities.SmoveConstants;

/**
 * Created by Manuramv on 8/28/2018.
 */

public class BookingAvailabilityServiceImpl {

    private Context mActivityObj;

    public BookingAvailabilityServiceImpl(Context mActivity) {
        this.mActivityObj = mActivity;
    }

    public void getBookingAvailabilityInfo(String startTIme, String endTime) {
        try {
            if(CommonUtils.isConnectingToInternet(mActivityObj.getApplicationContext())){
                String paymentStatusUrl = SmoveConstants.END_POINT+"availability?startTime="+startTIme+"&endTime="+endTime;
                Uri bookingAvailabilityURL = Uri.parse(paymentStatusUrl);


                //CommonUtils.showBusyIndicator(mActivityObj);
                new ApiClientRequest(mActivityObj.getApplicationContext()).getParkwayService().getBookingAvailabilityAPI(bookingAvailabilityURL).enqueue(new Callback<GetBookingAvailabilityResponse>() {
                    @Override
                    public void onResponse(Call<GetBookingAvailabilityResponse> call, Response<GetBookingAvailabilityResponse> response) {

                    }

                    @Override
                    public void onFailure(Call<GetBookingAvailabilityResponse> call, Throwable t) {

                    }

                });
            } else {
                //  utils.showCustomPopupMessage(mActivityObj, mActivityObj.getResources().getString(R.string.internet_warn));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
