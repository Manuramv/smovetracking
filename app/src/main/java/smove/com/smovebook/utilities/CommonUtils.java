package smove.com.smovebook.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Manuramv on 8/28/2018.
 */

public class CommonUtils {
    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = connectivity.getActiveNetworkInfo();

        boolean isConnected = info != null && info.isConnectedOrConnecting();
        return isConnected;

    }

    public static String convertToUnixTImestamp(String timeToCOnvert) {
        long unixTime = 0;
        try {
            //new java.util.Date(Long.parseLong(timeToCOnvert));

            //String dateString = "Fri, 09 Nov 2012 23:40:18 GMT";
            DateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm");
            Date date = dateFormat.parse(timeToCOnvert);
             unixTime = (long) date.getTime() / 1000;
            System.out.println("UNix time==="+unixTime);//<- prints 1352504418
        } catch (Exception e){
            Log.d("TAG","Exception while converting the date to unix::"+e);
        }
        return  String.valueOf(unixTime);
    }
}
