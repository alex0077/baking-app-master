package com.example.bakingapp.ingredients;

import com.example.bakingapp.models.Recipe;

public class IngredientsPresenter implements IngredientsPresenterInterface {

    private IngredientsViewInterface recipeDetailsViewInterface;
    private Recipe recipe;
    public IngredientsPresenter(IngredientsActivity recipeDetailsActivity, Recipe selectedRecipe) {
        this.recipeDetailsViewInterface =recipeDetailsActivity;
        this.recipe = selectedRecipe;
    }

    public void getRecipeIngredients() {
       recipeDetailsViewInterface.displayRecipesIngredients(recipe);
    }
}
