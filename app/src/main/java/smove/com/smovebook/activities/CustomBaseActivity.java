package smove.com.smovebook.activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import smove.com.smovebook.R;


/**
 * Created by Manuramv on 8/28/2018.
 */

public class CustomBaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_base_view);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    public void setAppToolbar(String title, int resourceId) {
        Toolbar actionBarToolbar = (Toolbar) findViewById(R.id.tool_bar);
        actionBarToolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        setSupportActionBar(actionBarToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(resourceId);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        TextView mTitleTextView = (TextView) findViewById(R.id.toolbar_titleText);
        mTitleTextView.setText(title);

    }

}
