package com.example.cookmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cookmaster.ApiService.ApiClient;
import com.example.cookmaster.model.Event;
import com.example.cookmaster.model.Goesto;
import com.example.cookmaster.model.IngredientAdapter;
import com.example.cookmaster.model.Ingredients;
import com.example.cookmaster.model.Logins;
import com.example.cookmaster.model.Recipe;
import com.example.cookmaster.model.RecipeIngredient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class TutoActivity extends AppCompatActivity {

    private Context context;
    private WebView webView;

    private Recipe recipe;




    public ArrayList<Ingredients> recipeIngredient = new ArrayList<>();

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuto);
        context = getApplicationContext();
        TextView textView1 = findViewById(R.id.textview1);
        TextView textView2 = findViewById(R.id.textview2);
        TextView textView3 = findViewById(R.id.textview3);

        ListView listView = findViewById(R.id.listView);
        IngredientAdapter adapter = new IngredientAdapter(this, recipeIngredient);
        listView.setAdapter(adapter);


        Intent intent = getIntent();
        int recipe_id = intent.getIntExtra("recipe_id",0);

        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        String videoId = "kqVzOjfRZPk "; // Identifiant de la vidéo YouTube
        String videoUrl = "https://www.youtube.com/embed/" + videoId;
        String html = "<iframe width=\"100%\" height=\"100%\" src=\"" + videoUrl + "\" frameborder=\"0\" allowfullscreen></iframe>";
        webView.loadData(html, "text/html", "utf-8");

        if(recipe_id != 0 ){
            System.out.println(recipe_id);

            CountDownLatch latch = new CountDownLatch(1);
            AtomicBoolean result = new AtomicBoolean(false);

            new Thread(() -> {
                String token = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("token","");
                ApiClient.Recipe getRecipe = ApiClient.getRetrofitInstance().create(ApiClient.Recipe.class);
                JsonObject requestBody2 = new JsonObject();
                requestBody2.addProperty("id", recipe_id);
                Call<ResponseBody> call2 = getRecipe.getrecipe(requestBody2,token);

                try {
                    Response<ResponseBody> response2 = call2.execute();
                    if (response2.isSuccessful()) {
                        assert response2.body() != null;
                        String responseString = response2.body().string();
                        Gson gson = new Gson();
                        JsonObject jsonObject = gson.fromJson(responseString, JsonObject.class);
                        if (jsonObject.has("recipes")) {
                            JsonArray recipesArray = jsonObject.getAsJsonArray("recipes");
                            if (recipesArray.size() > 0) {
                                JsonObject recipeObject = recipesArray.get(0).getAsJsonObject();
                                recipe = gson.fromJson(recipeObject, Recipe.class);
                                result.set(true);
                            }
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
                String token = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("token","");
                ApiClient.RecipeIngredient getRecipeIngredient = ApiClient.getRetrofitInstance().create(ApiClient.RecipeIngredient.class);
                JsonObject requestBody2 = new JsonObject();
                requestBody2.addProperty("recipeid", recipe_id);
                Call<ResponseBody> call2 = getRecipeIngredient.getRecipeIngredient(requestBody2,token);

                try {
                    Response<ResponseBody> response2 = call2.execute();
                    if (response2.isSuccessful()) {
                        assert response2.body() != null;
                        String responseString = response2.body().string();
                        Gson gson = new Gson();
                        JsonObject jsonObject = gson.fromJson(responseString, JsonObject.class);
                        if (jsonObject.has("recipeIngredients")) {
                            recipeIngredient.addAll(gson.fromJson(jsonObject.get("recipeIngredients"), new TypeToken<ArrayList<Ingredients>>() {}.getType()));
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

            if(recipeIngredient.size()  > 0 ){
                textView1.setText(recipe.getDescription());
                textView2.setText("Durée en MN : "+ recipe.getDuration());
                textView3.setText("Difficulté : "+ recipe.getComplexityLevel());
            }else {
                textView1.setText("Recette indisponible pour ce WorkShop");
                textView2.setVisibility(View.INVISIBLE);
                textView3.setVisibility(View.INVISIBLE);
            }


        }else {
            textView1.setText("Recette indisponible pour ce WorkShop");
            textView2.setVisibility(View.INVISIBLE);
            textView3.setVisibility(View.INVISIBLE);
        }




    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webView != null && webView.getParent() != null) {
            webView.onPause();
            webView.pauseTimers();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webView != null && webView.getParent() != null) {
            webView.onResume();
            webView.resumeTimers();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            // Libérer les ressources du WebView
            webView.stopLoading();
            webView.setWebChromeClient(null);
            webView.setWebViewClient(null);
            if (webView.getParent() != null) {
                ((ViewGroup) webView.getParent()).removeView(webView);
            }
            webView.destroy();
        }
    }



    @Override
    public void onBackPressed() {
        // Libérer les ressources du WebView lorsque l'utilisateur quitte l'activité
        webView.loadUrl("about:blank");
        webView.stopLoading();
        webView.setWebChromeClient(null);
        webView.setWebViewClient(null);
        webView.destroy();

        super.onBackPressed();
    }
}
