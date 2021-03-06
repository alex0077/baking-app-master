package com.example.bakingapp.stepInstructions;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;

import com.example.bakingapp.R;
import com.example.bakingapp.models.StepInstructions;

public class StepDetailActivity extends AppCompatActivity {

    private static final String TAG = "StepDetail" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StepInstructions selectedStepInstructions = getIntent().getExtras().getParcelable(StepDetailFragment.ARG_ITEM_ID);
        Log.i(TAG, "onCreate: "+ selectedStepInstructions.getDescription());

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(StepDetailFragment.ARG_ITEM_ID,
                    getIntent().getExtras().getParcelable(StepDetailFragment.ARG_ITEM_ID));
            StepDetailFragment fragment = new StepDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
