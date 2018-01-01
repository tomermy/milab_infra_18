package com.inclass.tomermai.restexerciseapp;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeFetcher {
    private RequestQueue m_Queue;
    private final static String REQUEST_URL = "https://sleepy-eyrie-53831.herokuapp.com/getTime";

    public class TimeResponse {
        public boolean isError;
        public String timestamp;
        public String zone;

        public TimeResponse(boolean isError, String time, String zone) {
            this.isError = isError;
            this.timestamp = time;
            this.zone = zone;
        }
    }

    public interface TimeResponseListener {
        void onResponse(TimeResponse response);
    }

    public TimeFetcher(Context context) {
        m_Queue = Volley.newRequestQueue(context);
    }

    private TimeResponse createErrorResponse() {
        return new TimeResponse(true, null, null);
    }

    private String extractTimeFromTimestamp(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm:ss a", Locale.ENGLISH);

        // todo: Update once TimeZone conversion is available on server
        timeFormat.setTimeZone(TimeZone.getDefault());
        return timeFormat.format(date);
    }

    public void dispatchRequest(final String zone, final TimeResponseListener listener) {

        // todo: add zone to body or request params based on future implementation
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, REQUEST_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject dataPayload = response.getJSONObject("data");
                            TimeResponse res = new TimeResponse(false,
                                    extractTimeFromTimestamp(dataPayload.getLong("time")),
                                    dataPayload.getString("zone")
                            );
                            listener.onResponse(res);
                        }
                        catch (JSONException e) {
                            listener.onResponse(createErrorResponse());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponse(createErrorResponse());
            }
        });

        m_Queue.add(req);
    }
}

