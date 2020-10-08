package com.rku.tutorial_12;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class MainActivity extends AppCompatActivity {

    RecyclerView rcvView;
    LinearLayoutManager linearLayoutManager;
    RequestQueue requestQueue;
    JsonArrayRequest jsonArrayRequest;

    UserAdapter userAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcvView = findViewById(R.id.rcvView);
        rcvView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        rcvView.setLayoutManager(linearLayoutManager);

        //getting divider in recycle view
        DividerItemDecoration itemDecoration = new DividerItemDecoration(rcvView.getContext(), LinearLayoutManager.VERTICAL);
        rcvView.addItemDecoration(itemDecoration);

        //added animation
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getApplicationContext(), resId);
        rcvView.setLayoutAnimation(animation);

        volleyNetworkCall();
    }

    private void volleyNetworkCall() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                Util.URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Util.jsonArray = response;
                        userAdapter = new UserAdapter(response);
                        rcvView.setAdapter(userAdapter);
                        userAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
    }
}