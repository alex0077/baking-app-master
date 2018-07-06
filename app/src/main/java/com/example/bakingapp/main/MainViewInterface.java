package com.example.bakingapp.main;

import com.example.bakingapp.models.Recipe;

import java.util.List;

interface MainViewInterface {
    void displayRecipes(List<Recipe> recipes);
    void displayError(String e);
}
