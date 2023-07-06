package com.example.cookmaster;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookmaster.ApiService.ApiClient;
import com.example.cookmaster.model.Event;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class ActivityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_REGISTERED = 0;
    private static final int TYPE_UNREGISTERED = 1;
    private static final int TYPE_SEPARATOR = 2;

    private ArrayList<Event> registeredActivities;
    private ArrayList<Event> unregisteredActivities;

    private int id;
    private Context context;

    public ActivityAdapter(ArrayList<Event> registeredActivities, ArrayList<Event> unregisteredActivities, int id , Context context ) {
        this.registeredActivities = registeredActivities;
        this.unregisteredActivities = unregisteredActivities;
        this.id = id;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_REGISTERED) {
            View itemView = inflater.inflate(R.layout.item_activity, parent, false);
            return new RegisteredActivityViewHolder(itemView);
        } else if (viewType == TYPE_UNREGISTERED) {
            View itemView = inflater.inflate(R.layout.item_activity, parent, false);
            return new UnregisteredActivityViewHolder(itemView);
        } else {
            View itemView = inflater.inflate(R.layout.item_separator, parent, false);
            return new SeparatorViewHolder(itemView);
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof RegisteredActivityViewHolder) {
            if (!registeredActivities.isEmpty() && position < registeredActivities.size()) {
                Event activity = registeredActivities.get(position);
                RegisteredActivityViewHolder registeredViewHolder = (RegisteredActivityViewHolder) viewHolder;
                registeredViewHolder.tvActivityDate.setText(formatDate(activity.getStartDate(),Locale.FRENCH));
                registeredViewHolder.tvActivityDescription.setText(activity.getDescription());
                registeredViewHolder.btnJoinLeave.setText(R.string.leave);
                registeredViewHolder.btnJoinLeave.setBackgroundColor(Color.parseColor("#FF0000"));
                registeredViewHolder.btnJoinLeave.setOnClickListener(v -> leaveActivity(activity));
                registeredViewHolder.btnJoinLeave.setVisibility(View.VISIBLE);
                registeredViewHolder.btnView.setVisibility(View.VISIBLE);
                registeredViewHolder.btnView.setText("voir");
                registeredViewHolder.btnView.setOnClickListener(v -> seeActivity(activity));
            }
        } else if (viewHolder instanceof UnregisteredActivityViewHolder) {
            if (!unregisteredActivities.isEmpty()) {
                int unregisteredPosition = position - registeredActivities.size() - 1;
                if (unregisteredPosition >= 0 && unregisteredPosition < unregisteredActivities.size()) {
                    Event activity = unregisteredActivities.get(unregisteredPosition);
                    UnregisteredActivityViewHolder unregisteredViewHolder = (UnregisteredActivityViewHolder) viewHolder;
                    unregisteredViewHolder.tvActivityDate.setText(formatDate(activity.getStartDate(),Locale.FRENCH));
                    unregisteredViewHolder.tvActivityDescription.setText(activity.getDescription());
                    unregisteredViewHolder.btnJoinLeave.setText(R.string.join);
                    unregisteredViewHolder.btnJoinLeave.setBackgroundColor(Color.parseColor("#008000"));
                    unregisteredViewHolder.btnJoinLeave.setOnClickListener(v -> joinActivity(activity));
                    unregisteredViewHolder.btnJoinLeave.setVisibility(View.VISIBLE);
                    unregisteredViewHolder.btnView.setVisibility(View.INVISIBLE);

                }
            }
        } else if (viewHolder instanceof SeparatorViewHolder) {
            SeparatorViewHolder separatorViewHolder = (SeparatorViewHolder) viewHolder;
            separatorViewHolder.tvSeparator.setText("Activités à Rejoindre");
        }
    }

    private String formatDate(String dateString, Locale locale) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale);
            Date date = inputFormat.parse(dateString);

            SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE d MMMM HH'H'mm", locale);
            return outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    @Override
    public int getItemCount() {
        return registeredActivities.size() + unregisteredActivities.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < registeredActivities.size()) {
            return TYPE_REGISTERED;
        } else if (position == registeredActivities.size()) {
            return TYPE_SEPARATOR;
        } else {
            return TYPE_UNREGISTERED;
        }
    }


    private void joinActivity(Event activity) {
        System.out.println(activity.getStartDate());
        registeredActivities.add(activity);
        unregisteredActivities.remove(activity);
        notifyDataSetChanged();

        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            String token = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("token","");
            ApiClient.GoActivities getActivites = ApiClient.getRetrofitInstance().create(ApiClient.GoActivities.class);
            JsonObject requestBody2 = new JsonObject();
            requestBody2.addProperty("id_user", id);
            requestBody2.addProperty("id_event", activity.getId());
            Call<ResponseBody> call2 = getActivites.addActivites(requestBody2,token);

            try {
                Response<ResponseBody> response2 = call2.execute();
                if (response2.isSuccessful()) {
                    assert response2.body() != null;
                    String responseString = response2.body().string();
                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(responseString, JsonObject.class);
                    if (jsonObject.has("true")) {

                    }
                } else {
                    Log.e("API Error", "Code: " + response2.code() + ", Message: " + response2.message());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }



    private void seeActivity(Event activity) {
        Intent intent = new Intent(context, TutoActivity.class);
        intent.putExtra("recipe_id", activity.getRecipe_id());

        context.startActivity(intent);
    }





    private void leaveActivity(Event activity) {
        System.out.println(activity.getRecipe_id());
        registeredActivities.remove(activity);
        unregisteredActivities.add(activity);
        notifyDataSetChanged();

        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            String token = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("token","");
            ApiClient.GoActivities deleteActivites = ApiClient.getRetrofitInstance().create(ApiClient.GoActivities.class);
            JsonObject requestBody2 = new JsonObject();
            requestBody2.addProperty("id_user", id);
            requestBody2.addProperty("id_event", activity.getId());
            Call<ResponseBody> call2 = deleteActivites.deleteActivites(requestBody2,token);

            try {
                Response<ResponseBody> response2 = call2.execute();
                if (response2.isSuccessful()) {
                    assert response2.body() != null;
                    String responseString = response2.body().string();
                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(responseString, JsonObject.class);
                    if (jsonObject.has("true")) {

                    }
                } else {
                    Log.e("API Error", "Code: " + response2.code() + ", Message: " + response2.message());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static class RegisteredActivityViewHolder extends RecyclerView.ViewHolder {
        TextView tvActivityDate;
        TextView tvActivityDescription;
        Button btnJoinLeave;
        Button btnView;

        RegisteredActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            tvActivityDate = itemView.findViewById(R.id.tvActivityDate);
            tvActivityDescription = itemView.findViewById(R.id.tvActivityDescription);
            btnJoinLeave = itemView.findViewById(R.id.btnJoinLeave);
            btnView = itemView.findViewById(R.id.btnView);
        }
    }

    private static class UnregisteredActivityViewHolder extends RecyclerView.ViewHolder {
        TextView tvActivityDate;
        TextView tvActivityDescription;
        Button btnJoinLeave;
        Button btnView;

        UnregisteredActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            tvActivityDate = itemView.findViewById(R.id.tvActivityDate);
            tvActivityDescription = itemView.findViewById(R.id.tvActivityDescription);
            btnJoinLeave = itemView.findViewById(R.id.btnJoinLeave);
            btnView = itemView.findViewById(R.id.btnView);
        }
    }

    private static class SeparatorViewHolder extends RecyclerView.ViewHolder {
        TextView tvSeparator;

        SeparatorViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSeparator = itemView.findViewById(R.id.tvSeparator);
        }
    }
}
