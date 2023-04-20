package com.example.cookmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.cookmaster.databinding.ActivityMenuBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    ActivityMenuBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new AcceuilFragment());
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Set the default selection to the Home fragment
        bottomNavigationView.setSelectedItemId(R.id.acceuil);

        binding.bottomNavigationView.setOnItemSelectedListener( item -> {

           switch (item.getItemId()){
               case R.id.activite:
                   replaceFragment(new ActiviteFragment());
                   break;
               case R.id.acceuil:
                   replaceFragment(new AcceuilFragment());
                   break;
               case R.id.profil:
                   replaceFragment(new ProfilFragment());
                   break;
           }

            return true;
        });
    }
    private void replaceFragment (Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();

    }
}