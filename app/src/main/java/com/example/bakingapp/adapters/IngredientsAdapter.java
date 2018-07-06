package com.example.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakingapp.R;
import com.example.bakingapp.models.Ingredient;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    private List<Ingredient> ingredientList;
    private Context context;
    public IngredientsAdapter(List<Ingredient> ingredientList,Context context){
        this.ingredientList = ingredientList;
        this.context = context;
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item_ingredients,parent,false);
        return new IngredientsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        Ingredient currentIngredient = ingredientList.get(position);
        StringBuilder builder = new StringBuilder();
        holder.ingredientsTextView.setText(builder.append(position + 1).append(".").append(currentIngredient.getIngredient()).append(" ").append(currentIngredient.getQuantity()).append(" ").append(currentIngredient.getMeasure())
        );
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {
        private TextView ingredientsTextView;
        IngredientsViewHolder(View itemView) {
            super(itemView);
            ingredientsTextView = itemView.findViewById(R.id.ingredient_textview);
        }
    }

}
