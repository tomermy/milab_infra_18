package com.inclass.tomermai.restexerciseapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivityFragment firstAppFragment = new MainActivityFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, firstAppFragment).commit();
    }

    public void fetchTime(final View view) {
        final TimeFetcher timeFetcher = new TimeFetcher(view.getContext());
        Editable timeZoneInput = ((EditText) findViewById(R.id.edit_zone)).getText();
        final String zone = timeZoneInput.toString();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Time...");
        progressDialog.show();

        timeFetcher.dispatchRequest(zone, new TimeFetcher.TimeResponseListener() {
            @Override
            public void onResponse(TimeFetcher.TimeResponse response) {
                progressDialog.hide();

                if (response.isError) {
                    Toast.makeText(view.getContext(),
                            "Error fetching timestamp", Toast.LENGTH_LONG).show();
                    return;
                }

                ((TextView)MainActivity.this.findViewById(R.id.text_time)).setText(response.timestamp);
                ((TextView)MainActivity.this.findViewById(R.id.text_zone)).setText(String.valueOf(response.zone));
            }
        });
    }
}
