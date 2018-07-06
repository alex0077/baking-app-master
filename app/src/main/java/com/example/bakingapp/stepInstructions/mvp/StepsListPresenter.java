package com.example.bakingapp.stepInstructions.mvp;

import com.example.bakingapp.models.Recipe;


public class StepsListPresenter implements StepsListPresenterInterface {

    private StepsListViewInterface stepsListViewInterface;
    private Recipe selectedRecipe;
    public StepsListPresenter(StepsListViewInterface stepsListViewInterface, Recipe selectedRecipe){
        this.stepsListViewInterface = stepsListViewInterface;
        this.selectedRecipe = selectedRecipe;
    }

    public void getRecipeSteps() {
        stepsListViewInterface.displayRecipeSteps(selectedRecipe);
    }
}

