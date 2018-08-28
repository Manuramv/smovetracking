package smove.com.smovebook.activities;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import retrofit2.Response;
import smove.com.smovebook.R;
import smove.com.smovebook.networking.response.bookingapi.GetBookingAvailabilityResponse;
import smove.com.smovebook.serviceimpl.BookingAvailabilityServiceImpl;
import smove.com.smovebook.utilities.CommonUtils;

public class MainActivity extends CustomBaseActivity implements View.OnClickListener {
    Button btnDatePicker, btnTimePicker;
    Button btnFromTime,btnToTime,btnFindTaxi;
    EditText txtDate, txtTime;
    EditText etFromTime,etToTime;
    private int mYear, mMonth, mDay, mHour, mMinute,mSecond;
    private long time;
    private String date_time;
    Calendar calendar=null;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnFromTime = (Button) findViewById(R.id.btn_from);
        btnToTime = (Button) findViewById(R.id.btn_toTime);
        btnFindTaxi = (Button) findViewById(R.id.btn_findTaxi);
        etFromTime = (EditText) findViewById(R.id.et_fromTime);
        etToTime = (EditText) findViewById(R.id.et_toTime);
       /* btnDatePicker = (Button) findViewById(R.id.btn_date);
        btnTimePicker = (Button) findViewById(R.id.btn_time);
        txtDate = (EditText) findViewById(R.id.in_date);
        txtTime = (EditText) findViewById(R.id.in_time);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);*/
        btnFromTime.setOnClickListener(this);
        btnToTime.setOnClickListener(this);
        btnFindTaxi.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v == btnFromTime) {
            showDatenTimePicker(etFromTime);
        } else if( v == btnToTime){
            showDatenTimePicker(etToTime);
        } else if( v == btnFindTaxi){
            showTaxiAvailability();
        }

       /* if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
*/
    }



    public void showDatenTimePicker(final EditText etSetTime){
        final View dialogView = View.inflate(this, R.layout.date_time_picker, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
       // final Calendar calendar=null;
        dialogView.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);

                 calendar = new GregorianCalendar(datePicker.getYear(),
                        datePicker.getMonth(),
                        datePicker.getDayOfMonth());
                        /*timePicker.getCurrentHour(),
                        timePicker.getCurrentMinute());*/

                time = calendar.getTimeInMillis();
                alertDialog.dismiss();
            }});
        alertDialog.setView(dialogView);
        alertDialog.show();

        Button setDate = (Button) dialogView.findViewById(R.id.date_time_set);
        setDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                date_time = datePicker.getDayOfMonth() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getYear();
                alertDialog.dismiss();
                showtimePicker(etSetTime);
            }
        });
    }

    private void showtimePicker(final EditText etSetTime) {
        Log.d("TAG","show timer now");
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mMinute = c.get(Calendar.SECOND);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,int minute) {

                        mHour = hourOfDay;
                        mMinute = minute;
                        Log.d("TAG","selected time::"+date_time+" "+hourOfDay + ":" + minute);
                        etSetTime.setText(date_time+" "+hourOfDay+":"+minute+ mSecond);

                       // et_show_date_time.setText(date_time+" "+hourOfDay + ":" + minute);

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void showTaxiAvailability() {
        Log.d("TAG","show Taxi Availability");
        //convertToUnixTImestamp(etFromTime.getText().toString());
        BookingAvailabilityServiceImpl bookingAvailabilityServiceObj = new BookingAvailabilityServiceImpl(MainActivity.this);
        bookingAvailabilityServiceObj.getBookingAvailabilityInfo(CommonUtils.convertToUnixTImestamp(etFromTime.getText().toString()),CommonUtils.convertToUnixTImestamp(etToTime.getText().toString()));
    }

    public void taxiAvailabilityResponse(Response<GetBookingAvailabilityResponse> response){
        try {
            if (response != null && response.body().getData() != null) {
                Intent intent = new Intent(this, BookingMapActivity.class);
                startActivity(intent);
            }
        } catch (Exception e){
            Log.d("TAG","something went wrong");
        }
    }


}
