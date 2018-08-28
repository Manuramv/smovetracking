package smove.com.smovebook.utilities;

import android.content.Context;

/**
 * Created by Manuramv on 8/28/2018.
 */

public class SmoveApplication {
    private static Context mContext;

    public static Context getmContext() {
        return mContext;
    }

    public static void setmContext(Context mContext) {
        SmoveApplication.mContext = mContext;
    }
}
