package com.example.bakingapp.stepInstructions.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.bakingapp.R;

import com.example.bakingapp.adapters.StepInstructionsAdapter;
import com.example.bakingapp.models.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepListActivity extends AppCompatActivity implements StepsListViewInterface {

    @BindView(R.id.step_list_rv)
    RecyclerView recipeStepsRV;

    private StepsListPresenter stepsListPresenter;

    private boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(getTitle());
        ButterKnife.bind(this);
        if (findViewById(R.id.step_detail_container) != null) {
            mTwoPane = true;
        }

        setupMVP();
        setupRecyclerView();
        getSteps();
    }

    private void setupMVP() {
        stepsListPresenter = new StepsListPresenter(StepListActivity.this, getSelectedRecipe());
    }

    private void setupRecyclerView() {
        StepInstructionsAdapter stepInstructionsAdapter = new StepInstructionsAdapter(getSelectedRecipe().getSteps(), StepListActivity.this, mTwoPane);
        recipeStepsRV.setAdapter(stepInstructionsAdapter);
    }

    @Override
    public void displayRecipeSteps(Recipe recipe) {
        if (recipe.getIngredients() != null) {
            StepInstructionsAdapter stepInstructionsAdapter = new StepInstructionsAdapter(recipe.getSteps(), this, mTwoPane);
            recipeStepsRV.setLayoutManager(new LinearLayoutManager(this));
            recipeStepsRV.setAdapter(stepInstructionsAdapter);
        } else {
            showToast("No ingredients to display");
        }
    }

    public void displayError(String e) {
        showToast(e);
    }

    public void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    private void getSteps() {
        stepsListPresenter.getRecipeSteps();
    }

    private Recipe getSelectedRecipe() {
        Bundle data = getIntent().getExtras();
        return (Recipe) data.getParcelable("selected_recipe");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
