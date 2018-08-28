package smove.com.smovebook.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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
}
