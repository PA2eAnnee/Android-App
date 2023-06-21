package com.example.cookmaster.model;

public class RecipeIngredient {
    private int RecipeID;
    private int IngredientID;
    private float Quantity;

    public RecipeIngredient(int recipeID, int ingredientID, float quantity) {
        this.RecipeID = recipeID;
        this.IngredientID = ingredientID;
        this.Quantity = quantity;
    }

    // Getter and Setter methods
    public int getRecipeID() {
        return RecipeID;
    }

    public void setRecipeID(int recipeID) {
        this.RecipeID = recipeID;
    }

    public int getIngredientID() {
        return IngredientID;
    }

    public void setIngredientID(int ingredientID) {
        this.IngredientID = ingredientID;
    }

    public float getQuantity() {
        return Quantity;
    }

    public void setQuantity(float quantity) {
        this.Quantity = quantity;
    }
}

