package smove.com.smovebook.utilities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import smove.com.smovebook.R;

/**
 * Created by Manuramv on 8/28/2018.
 */

public class CommonUtils {
    static AlertDialog alertDialog;
    static ProgressDialog plainProgressIndicator=null;
    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = connectivity.getActiveNetworkInfo();

        boolean isConnected = info != null && info.isConnectedOrConnecting();
        return isConnected;

    }
//convert the time to unix time stamp.
    public static long convertToUnixTImestamp(String timeToCOnvert) {
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
        return  unixTime;
    }

//MEthod to show the popup messages.
    public static void showCustomPopupMessage(final Activity mContext, String msg){
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = null;
        LayoutInflater inflaterObjRef = null;
        View layoutObjRef = null;
        Button dontAllowButtonRef = null;
        TextView messageText;
        Button allowButtonRef = null;
        try {
            // mFragmentObjGlobal=mFragmentObj;
            alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(mContext);
            alertDialogBuilder.setCancelable(false);
            inflaterObjRef = mContext.getLayoutInflater();
            layoutObjRef = inflaterObjRef.inflate(R.layout.popup_screen, null);
            alertDialogBuilder.setView(layoutObjRef);
            dontAllowButtonRef = (Button) layoutObjRef.findViewById(R.id.okbutton);
            messageText = (TextView) layoutObjRef.findViewById(R.id.messageText);
            messageText.setText(msg);
            // allowButtonRef = (Button) layoutObjRef.findViewById(R.id.allow_button);
            dontAllowButtonRef.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //CommonUtils.setStringKeyPreferences(ParkwayConstants.IS_USERCLICKED_ONALLOW_DISALLOW, ParkwayConstants.IS_USERCLICKED_ONALLOW_DISALLOW_DISALLOW);
                    alertDialog.dismiss();
                }
            });
            alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//To show the loader
    public static void showBusyIndicator(final AppCompatActivity activityObj) {
        try {
            if (activityObj != null) {
                activityObj.runOnUiThread(new Runnable() {
                    public void run() {
                        if (plainProgressIndicator == null)
                            plainProgressIndicator = new ProgressDialog(activityObj);

                        if (!(plainProgressIndicator.isShowing())) {
                            plainProgressIndicator = ProgressDialog.show(activityObj, "", "Loading", true, false);
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//dismiss the progress bar.
    public static void removeBusyIndicator(final AppCompatActivity activityObj) {
        try {
            activityObj.runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        if (plainProgressIndicator != null && plainProgressIndicator.isShowing()) {
                            plainProgressIndicator.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
