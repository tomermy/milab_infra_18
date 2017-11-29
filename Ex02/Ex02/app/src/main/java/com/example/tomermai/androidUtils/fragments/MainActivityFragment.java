package com.example.tomermai.androidUtils.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tomermai.androidUtils.FamilyListActivity;
import com.example.tomermai.androidUtils.services.NotificationsService;
import com.example.tomermai.androidUtils.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public static final int STARKS_ID = 1;
    public static final int LANNISTER_ID = 2;
    public static final String EXTRA_FAMILY = "com.example.tomermai.ex2.FAMILY";
    View mainViewFragment;

    public MainActivityFragment() {
    }

    /**
     * Called when the user taps the families button
     */
    public void startFiguresActivity(View view, int familyID) {
        Intent intent = new Intent(this.getActivity(), FamilyListActivity.class);
        intent.putExtra(EXTRA_FAMILY, familyID);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainViewFragment = inflater.inflate(R.layout.fragment_main, container, false);

        // Toast
        final TextView promptTextView = mainViewFragment.findViewById(R.id.text_prompt_home);
        Button showTextButton = mainViewFragment.findViewById(R.id.show_main_text_b);

        // Game Of Thrones
        Button lannisterListButton = mainViewFragment.findViewById(R.id.lannister_button);
        Button starkListButton = mainViewFragment.findViewById(R.id.stark_button);

        // Notifications Service
        final EditText setTimeInterval = mainViewFragment.findViewById(R.id.set_notifications_time);
        Button spamServiceButton = mainViewFragment.findViewById(R.id.start_notification_service);

        // Google Search
        final RequestQueue queue = Volley.newRequestQueue(getContext());
        final String googleSearchUrlPrefix = "https://www.google.com/search?q=";
        final String charset = "UTF-8";

        final EditText googleQueryInput = mainViewFragment.findViewById(R.id.search_google);
        final TextView googleQueueryResult = mainViewFragment.findViewById(R.id.search_results);
        Button googleSearchButton = mainViewFragment.findViewById(R.id.google_search_button);

        googleSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Default value when encoding fails
                String queryUrl = googleSearchUrlPrefix + "";
                try {
                    queryUrl = String.format("%s%s", googleSearchUrlPrefix,
                            URLEncoder.encode(googleQueryInput.getText().toString(), charset));
                } catch (UnsupportedEncodingException e) {
                    Log.e("encoding_format", e.getMessage());
                }

                // Request an HTML string response from Google
                StringRequest stringRequest = new StringRequest(Request.Method.GET, queryUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                getTitleFromHtml(response, googleQueueryResult);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        googleQueueryResult.setText("Something went wrong!");
                    }
                });

                // Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });


        showTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String promptText = promptTextView.getText().toString();
                Toast.makeText(view.getContext(), promptText, Toast.LENGTH_SHORT).show();
            }
        });

        starkListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFiguresActivity(v, STARKS_ID);
            }
        });

        lannisterListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFiguresActivity(v, LANNISTER_ID);
            }
        });

        spamServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double alertsTimeIntervalMinutes = Double.parseDouble(setTimeInterval.getText().toString());
                long timeIntervalMili = Math.round(alertsTimeIntervalMinutes * 60 * 1000);
                startSpamActivity(timeIntervalMili);
            }
        });

        return mainViewFragment;
    }

    private void getTitleFromHtml(String response, TextView googleQueueryResult) {
        Document googleResponseHtml = Jsoup.parse(response);
        Elements results = googleResponseHtml.select("a._Olt").select("div._H1m");
        if (results.size() == 0) {
            googleQueueryResult.setText("No results found!");
            return;
        }
        String firstResultTitle = results.first().text();
        googleQueueryResult
                .setText(firstResultTitle);
    }

    public void startSpamActivity(long timeInterval) {
        NotificationsService.startActionNotifications(this.getActivity(), timeInterval);
    }
}
