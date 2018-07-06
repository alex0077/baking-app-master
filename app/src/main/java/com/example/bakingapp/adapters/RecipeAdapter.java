package com.example.bakingapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakingapp.R;
import com.example.bakingapp.models.Recipe;
import com.example.bakingapp.ingredients.IngredientsActivity;
import com.example.bakingapp.stepInstructions.mvp.StepListActivity;
import com.github.aakira.expandablelayout.ExpandableLayout;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> recipeList;
    private Context context;
    private final ListItemClickListener listItemClickListener;
    public RecipeAdapter(List<Recipe> recipeList, Context context, ListItemClickListener listItemClickListener){
        this.recipeList = recipeList;
        this.context = context;
        this.listItemClickListener = listItemClickListener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item_recipes,parent,false);
        return new RecipeViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final RecipeViewHolder holder, final int position) {

        holder.recipeNameTxt.setText(recipeList.get(position).getName());
        holder.recipeNameTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.expandableLayout.toggle();
            }
        });
        holder.recipeServingCountTxt.setText(context.getString(R.string.servings_label)+recipeList.get(position).getServings());
        holder.recipeStepCountTxt.setText("Number of steps: "+recipeList.get(position).getSteps().size());
        holder.recipeIngredientCountTxt.setText("Number of ingredients: "+recipeList.get(position).getIngredients().size());
        holder.cookingCardAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StepListActivity.class);
                intent.putExtra("selected_recipe",recipeList.get(position));
                context.startActivity(intent);
            }
        });
        holder.showIngredientsAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,IngredientsActivity.class);
                intent.putExtra("recipe",recipeList.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView recipeNameTxt;
        private ExpandableLayout expandableLayout;
        private TextView recipeIngredientCountTxt;
        private TextView recipeStepCountTxt;
        private TextView recipeServingCountTxt;
        private TextView cookingCardAction;
        private TextView showIngredientsAction;
        RecipeViewHolder(View itemView) {
            super(itemView);
            recipeNameTxt = itemView.findViewById(R.id.recipe_name_txt);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            recipeIngredientCountTxt = itemView.findViewById(R.id.recipe_ingredient_count_txt);
            recipeStepCountTxt = itemView.findViewById(R.id.recipe_step_count_txt);
            recipeServingCountTxt = itemView.findViewById(R.id.recipe_servings_count_txt);
            cookingCardAction = itemView.findViewById(R.id.cooking_card_action);
            showIngredientsAction = itemView.findViewById(R.id.show_ingredients_action);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPostion = getAdapterPosition();
            listItemClickListener.onListItemClick(clickedPostion,recipeList);
        }
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex,List<Recipe> recipeList);
    }

}
