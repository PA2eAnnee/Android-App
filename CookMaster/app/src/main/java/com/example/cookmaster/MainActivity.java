package com.example.cookmaster;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.cookmaster.ApiService.ApiClient;
import com.example.cookmaster.model.Connectoken;
import com.example.cookmaster.model.LoginRequest;
import com.example.cookmaster.model.Logins;
import com.example.cookmaster.model.Token;
import com.example.cookmaster.model.Users;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;


public class MainActivity extends BaseActivity  {
    EditText editTextEmail;
    EditText editTextPassword;
    Button buttonLogin;
    TextView buttonPassword;
    TextView textViewSignup;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des éléments de vue
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonPassword= findViewById(R.id.textViewForgotPassword);
        textViewSignup = findViewById(R.id.textViewSignup);


        if(verifToken()){
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        }


        // Ecouteur de clic sur le bouton de connexion
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Vérification des informations de connexion
                if (checkLogin(editTextEmail.getText().toString(), editTextPassword.getText().toString())) {
                    // Redirection vers le menu principal si les informations sont correctes
                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Affichage d'un message d'erreur si les informations sont incorrectes
                    Toast.makeText(MainActivity.this, "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });
      // Ecouteur de clic sur le lien de mot de passe oublié
        buttonPassword.setOnClickListener(view -> {
            // Redirection vers l'activité de reintialisation de mot de passe
            Intent intent = new Intent(MainActivity.this, ForgetPasswordActivity.class);
            startActivity(intent);
        });

        // Ecouteur de clic sur le lien de création de compte
        textViewSignup.setOnClickListener(view -> {
            // Redirection vers l'activité de création de compte
            Intent intent = new Intent(MainActivity.this, InscriptionActivity.class);
            startActivity(intent);
        });
    }
    private boolean checkLogin(final String email, final String password) {


        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean result = new AtomicBoolean(false);

        new Thread(() -> {

            // Construire un objet JSON à partir des données de connexion
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("email", email);
            jsonObject.addProperty("password", password);

            // Convertir l'objet JSON en une chaîne JSON
            String json = new Gson().toJson(jsonObject);

            ApiClient.Connection connection = ApiClient.getRetrofitInstance().create(ApiClient.Connection.class);
            Call<ResponseBody> call = connection.login(new Gson().fromJson(json, JsonObject.class));
            try {
                Response<ResponseBody> response = call.execute();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String responseBody = response.body().string();
                    Gson gson = new Gson();
                    Logins login = gson.fromJson(responseBody, Logins.class);// récupérer le corps de la réponse
                    // Vérifier la réponse de l'API pour confirmer ou infirmer la validité des identifiants de l'utilisateur
                   if (responseBody.contains("name")){
                       setLoginStatus(login);
                       result.set(true);
                    }
                } else {
                    // Afficher le code d'erreur et le message d'erreur dans les logs pour déboguer l'erreur
                    Log.e("API Error", "Code: " + response.code() + ", Message: " + response.message());
                    result.set(false);
                }

            } catch (IOException e) {
                e.printStackTrace();
                // Erreur de communication avec le serveur
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

        return result.get();
    }

    private boolean verifToken(){
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean result = new AtomicBoolean(false);


        new Thread(() -> {
            String token = getApplicationContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("token", "");

            Connectoken connectoken = new Connectoken(token);
            String json = new Gson().toJson(connectoken);

            ApiClient.ConnectionToken connectionToken = ApiClient.getRetrofitInstance().create(ApiClient.ConnectionToken.class);
            Call<ResponseBody> call = connectionToken.connectoken(new Gson().fromJson(json, JsonObject.class));
            try {
                Response<ResponseBody> response = call.execute();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String responseBody = response.body().string();
                    // Vérifier la réponse de l'API pour confirmer ou infirmer la validité des identifiants de l'utilisateur
                    if (!responseBody.contains("error")){
                        result.set(true);
                    }
                } else {
                    // Afficher le code d'erreur et le message d'erreur dans les logs pour déboguer l'erreur
                    Log.e("API Error", "Code: " + response.code() + ", Message: " + response.message());
                    result.set(false);
                }

            } catch (IOException e) {
                e.printStackTrace();
                // Erreur de communication avec le serveur
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

        return result.get();
    }



    private void setLoginStatus(Logins logins) {
        SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
        editor.putInt("id", logins.getConnection().getConnection().getId());
        editor.putString("name", logins.getConnection().getConnection().getName());
        editor.putString("first_name", logins.getConnection().getConnection().getFirst_name());
        editor.putString("username", logins.getConnection().getConnection().getUsername());
        editor.putString("email", logins.getConnection().getConnection().getEmail());
        editor.putInt("role", logins.getConnection().getConnection().getRole());
        editor.putString("subscription", logins.getConnection().getConnection().getSubscription());
        editor.putString("picture", logins.getConnection().getConnection().getPicture());
        editor.putString("creation_time", logins.getConnection().getConnection().getCreation_time());
        editor.putString("token", logins.getConnection().getConnection().getToken());
        editor.apply();
    }

}