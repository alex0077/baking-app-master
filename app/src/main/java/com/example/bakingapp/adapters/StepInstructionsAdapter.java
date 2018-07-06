package com.example.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakingapp.R;
import com.example.bakingapp.models.StepInstructions;
import com.example.bakingapp.stepInstructions.StepDetailActivity;
import com.example.bakingapp.stepInstructions.StepDetailFragment;

import java.util.List;

public class StepInstructionsAdapter extends RecyclerView.Adapter<StepInstructionsAdapter.StepsViewHolder> {

    private boolean twoPane;
    private List<StepInstructions> stepList;
    private Context context;

    public StepInstructionsAdapter(List<StepInstructions> stepsList, Context context, boolean twoPane) {
        this.stepList = stepsList;
        this.context = context;
        this.twoPane = twoPane;
    }

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            StepInstructions stepInstructions = (StepInstructions) view.getTag();
            if (twoPane) {
                Bundle arguments = new Bundle();
                arguments.putParcelable(StepDetailFragment.ARG_ITEM_ID, stepInstructions);
                StepDetailFragment fragment = new StepDetailFragment();
                fragment.setArguments(arguments);
                ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.step_detail_container, fragment)
                        .commit();
            } else {
                Intent intent = new Intent(context, StepDetailActivity.class);
                intent.putExtra(StepDetailFragment.ARG_ITEM_ID, stepInstructions);
                context.startActivity(intent);
            }
        }
    };


    @NonNull
    @Override
    public StepInstructionsAdapter.StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item_steps, parent, false);
        return new StepsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StepInstructionsAdapter.StepsViewHolder holder, int position) {
        holder.stepShortDescriptionTxt.setText(stepList.get(position).getShortDescription());
        holder.stepLongDescriptionTxt.setText(stepList.get(position).getDescription());
        holder.itemView.setTag(stepList.get(position));
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder {
        private TextView stepShortDescriptionTxt;
        private TextView stepLongDescriptionTxt;

        StepsViewHolder(View itemView) {
            super(itemView);
            stepShortDescriptionTxt = itemView.findViewById(R.id.step_short_description);
            stepLongDescriptionTxt = itemView.findViewById(R.id.step_long_description);
            itemView.setOnClickListener(mOnClickListener);
        }
    }
}
