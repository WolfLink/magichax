package me.nathanp.magichacks;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class Requests {
    private RequestQueue queue;
    private Response.ErrorListener onError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
        }
    };

    Requests(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    void get(String url, JSONObject params, Response.Listener<JSONObject> onResponse) {
        get(url, params, onResponse, onError);
    }

    void get(String url, final JSONObject params, Response.Listener<JSONObject> onResponse, Response.ErrorListener onError) {
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, params, onResponse, onError);
        queue.add(getRequest);
    }

    void post(String url, JSONObject data, Response.Listener<JSONObject> onResponse) {
        post(url, data, onResponse, onError);
    }

    void post(String url, final JSONObject data, Response.Listener<JSONObject> onResponse, Response.ErrorListener onError) {
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, data, onResponse, onError);
        queue.add(postRequest);
    }
}
