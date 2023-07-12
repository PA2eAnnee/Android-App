package com.example.cookmaster.ApiService;

import android.graphics.Picture;

import com.example.cookmaster.model.LoginRequest;
import com.example.cookmaster.model.Users;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;

public class ApiClient {

    private static final String BASE_URL = "https://api.cookmaster.best";

    private static Retrofit retrofit = null;
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }


    public interface Connection {
        @POST("/connection")
        Call<ResponseBody> login(@Body JsonObject body);
    }

    public interface ConnectionToken {
        @POST("/connectiontoken")

        Call<ResponseBody> connectoken(@Body JsonObject body);
    }

    public interface GetActivites {
        @POST("/getevents")

        Call<ResponseBody> allactivities(@Body JsonObject body,@Header("Authorization") String headerValue);
    }


    public interface GetGoActivities {
        @POST("/getgoestos")

        Call<ResponseBody> goactivites(@Body JsonObject body,@Header("Authorization") String headerValue);
    }

    public interface GoActivities {
        @POST("/deletegoestos")

        Call<ResponseBody> deleteActivites(@Body JsonObject body,@Header("Authorization") String headerValue);

        @POST("/goestos")

        Call<ResponseBody> addActivites(@Body JsonObject body,@Header("Authorization") String headerValue);

    }

    public interface Recipe {
        @POST("/getrecipe")

        Call<ResponseBody> getrecipe(@Body JsonObject body,@Header("Authorization") String headerValue);

    }

    public interface RecipeIngredient {
        @POST("/getrecipeIngredient")

        Call<ResponseBody> getRecipeIngredient(@Body JsonObject body,@Header("Authorization") String headerValue);

    }

    public interface SavePicture {
        @POST("/pictures")

        Call<ResponseBody> savepicture(@Header("Authorization") String headerValue, @Part MultipartBody.Part photo );

    }


}
