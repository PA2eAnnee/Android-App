package com.example.cookmaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookmaster.model.User;


public class MainActivity extends BaseActivity  {
    EditText editTextEmail;
    EditText editTextPassword;
    Button buttonLogin;
    TextView textViewSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        User user1 = new User();

        // Initialisation des éléments de vue
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSignup = findViewById(R.id.textViewSignup);

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

        // Ecouteur de clic sur le lien de création de compte
        textViewSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirection vers l'activité de création de compte
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean checkLogin(String email, String password) {
        // Vérification des informations de connexion à l'aide d'une base de données ou d'un service web
        // Renvoie true si les informations sont correctes, false sinon
        return true;
    }


}