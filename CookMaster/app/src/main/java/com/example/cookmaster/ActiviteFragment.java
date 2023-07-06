package com.example.cookmaster;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cookmaster.ApiService.ApiClient;
import com.example.cookmaster.R;
import com.example.cookmaster.model.Event;
import com.example.cookmaster.model.Goesto;
import com.example.cookmaster.model.Logins;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import androidx.recyclerview.widget.RecyclerView;


public class ActiviteFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    public ArrayList<Event> hisEvents = new ArrayList<>();
    public ArrayList<Event> allEvents = new ArrayList<>();

    public ActiviteFragment() {
        // Required empty public constructor
    }

    public static ActiviteFragment newInstance(String param1, String param2) {
        ActiviteFragment fragment = new ActiviteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getActivity() != null) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        CountDownLatch latch = new CountDownLatch(1);
        AtomicBoolean result = new AtomicBoolean(false);
        final ArrayList<Goesto> allParticipate = new ArrayList<>();

        new Thread(() -> {
            int id = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getInt("id",0);
            String token = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("token","");
            ApiClient.GetGoActivities getActivites = ApiClient.getRetrofitInstance().create(ApiClient.GetGoActivities.class);
            JsonObject requestBody2 = new JsonObject();
            requestBody2.addProperty("id_user", id);
            Call<ResponseBody> call2 = getActivites.goactivites(requestBody2,token);

            try {
                Response<ResponseBody> response2 = call2.execute();
                if (response2.isSuccessful()) {
                    assert response2.body() != null;
                    String responseString = response2.body().string();
                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(responseString, JsonObject.class);
                    if (jsonObject.has("goestos")) {
                        allParticipate.addAll(gson.fromJson(jsonObject.get("goestos"), new TypeToken<ArrayList<Goesto>>() {}.getType()));
                        result.set(true);
                    }
                } else {
                    Log.e("API Error", "Code: " + response2.code() + ", Message: " + response2.message());
                    result.set(false);
                }
            } catch (IOException e) {
                e.printStackTrace();
                result.set(false);
            } finally {
                latch.countDown();
            }
        }).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        CountDownLatch latch2 = new CountDownLatch(1);
        AtomicBoolean result2 = new AtomicBoolean(false);

        new Thread(() -> {
            String token = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("token","");
            ApiClient.GetActivites getAllActivites = ApiClient.getRetrofitInstance().create(ApiClient.GetActivites.class);
            JsonObject requestBody2 = new JsonObject();
            Call<ResponseBody> call2 = getAllActivites.allactivities(requestBody2,token);

            try {
                Response<ResponseBody> response2 = call2.execute();
                if (response2.isSuccessful()) {
                    assert response2.body() != null;
                    String responseString = response2.body().string();
                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(responseString, JsonObject.class);
                    if (jsonObject.has("events")) {
                        allEvents.addAll(gson.fromJson(jsonObject.get("events"), new TypeToken<ArrayList<Event>>() {}.getType()));
                        result2.set(true);
                    }
                } else {
                    Log.e("API Error", "Code: " + response2.code() + ", Message: " + response2.message());
                    result2.set(false);
                }
            } catch (IOException e) {
                e.printStackTrace();
                result2.set(false);
            } finally {
                latch2.countDown();
            }
        }).start();

        try {
            latch2.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        for(int i = 0 ; i< allParticipate.size() ; i++){
            for (int y = 0 ; y < allEvents.size() ; y++){
                if(allParticipate.get(i).getEventId() == allEvents.get(y).getId()){
                  hisEvents.add(allEvents.get(y));
                }
            }
        }

        for (int i = 0 ; i< hisEvents.size() ; i++){
            allEvents.remove(hisEvents.get(i));
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_activite, container, false);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new ActivityAdapter(hisEvents, allEvents,requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getInt("id",0),getContext()));
        return rootView;
    }

}
