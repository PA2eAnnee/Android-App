package com.example.cookmaster;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.cookmaster.MainActivity;
import com.example.cookmaster.R;

public class ProfilFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ProfilFragment() {
        // Required empty public constructor
    }

    public static ProfilFragment newInstance(String param1, String param2) {
        ProfilFragment fragment = new ProfilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        SharedPreferences profil = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        TextView profileNameTextView = view.findViewById(R.id.profile_name);
        profileNameTextView.setText(profil.getString("name", ""));
        profileNameTextView = view.findViewById(R.id.profile_email);
        profileNameTextView.setText(profil.getString("email", ""));
        profileNameTextView = view.findViewById(R.id.profile_subscription);
        profileNameTextView.setText(profil.getString("subscription", ""));
        profileNameTextView = view.findViewById(R.id.profile_role);
        profileNameTextView.setText(String.valueOf(profil.getInt("role", 0)));

        Button logoutButton = view.findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(v -> {
            // Supprimer le token dans les SharedPreferences
            SharedPreferences.Editor editor = profil.edit();
            editor.remove("token");
            editor.apply();

            // Rediriger vers l'activity MainActivity
            Intent intent = new Intent(requireContext(), MainActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });

        return view;
    }
}
