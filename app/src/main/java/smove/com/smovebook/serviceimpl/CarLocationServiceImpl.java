package smove.com.smovebook.serviceimpl;

import android.content.Context;
import android.net.Uri;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smove.com.smovebook.activities.MainActivity;
import smove.com.smovebook.networking.ApiClientRequest;
import smove.com.smovebook.networking.response.bookingapi.GetBookingAvailabilityResponse;
import smove.com.smovebook.networking.response.carlocation.GetCarLocationResponse;
import smove.com.smovebook.utilities.CommonUtils;
import smove.com.smovebook.utilities.SmoveConstants;

/**
 * Created by Manuramv on 8/29/2018.
 */

public class CarLocationServiceImpl {
    private Context mActivityObj;

    public CarLocationServiceImpl(Context mActivity) {
        this.mActivityObj = mActivity;
    }

    public void getCarLocationInfo() {
        try {
            if(CommonUtils.isConnectingToInternet(mActivityObj.getApplicationContext())){
                String paymentStatusUrl = SmoveConstants.END_POINT+"locations";
                Uri bookingAvailabilityURL = Uri.parse(paymentStatusUrl);


                //CommonUtils.showBusyIndicator(mActivityObj);
                new ApiClientRequest(mActivityObj.getApplicationContext()).getApiService().getCarLocationAPI(bookingAvailabilityURL).enqueue(new Callback<GetCarLocationResponse>() {
                    @Override
                    public void onResponse(Call<GetCarLocationResponse> call, Response<GetCarLocationResponse> response) {
                        if(mActivityObj instanceof MainActivity){
                            ((MainActivity) mActivityObj).carLocationResponse(response);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetCarLocationResponse> call, Throwable t) {

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
