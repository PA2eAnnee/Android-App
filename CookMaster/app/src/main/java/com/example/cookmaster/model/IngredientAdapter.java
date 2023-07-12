package com.example.cookmaster.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cookmaster.R;
import com.example.cookmaster.model.Ingredients;

import java.util.ArrayList;

public class IngredientAdapter extends ArrayAdapter<Ingredients> {
    private Context context;
    private ArrayList<Ingredients> ingredientsList;

    public IngredientAdapter(Context context, ArrayList<Ingredients> ingredientsList) {
        super(context, 0, ingredientsList);
        this.context = context;
        this.ingredientsList = ingredientsList;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.list_item_ingredient, parent, false);
        }

        Ingredients currentIngredient = ingredientsList.get(position);

        TextView ingredientNameTextView = listItemView.findViewById(R.id.ingredientNameTextView);
        ingredientNameTextView.setText(currentIngredient.getName()+ " ");

        TextView ingredientNameTextView2 = listItemView.findViewById(R.id.ingredientNameTextView2);
        String quantity = String.valueOf(currentIngredient.getQuantity());
        String unity = currentIngredient.getUnity();
        System.out.println(unity);
        if(unity == null){
            unity = "X";
        }
        ingredientNameTextView2.setText(quantity +  " " + unity);


        // Ajoutez d'autres TextView ou d'autres vues pour afficher d'autres informations sur l'ingrédient

        return listItemView;
    }
}
