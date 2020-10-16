package com.develop.recipes.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.develop.recipes.R
import com.develop.recipes.data.model.RecipeModel
import com.makeramen.roundedimageview.RoundedImageView

class RecipeAdapter(
    private var recipeList: List<RecipeModel>,
    private val context: Context,
    var clickListner: OnRecipeItemClickListner
) :

    RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_recipes, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel = recipeList.get(position)

        holder.initialize(dataModel, clickListner)
    }


    class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {
        lateinit var tvName: TextView
        lateinit var tvHeadline: TextView
        lateinit var tvFat: TextView
        lateinit var tvCalories: TextView
        lateinit var imgRecipe: RoundedImageView

        init {
            imgRecipe = itemLayoutView.findViewById(R.id.img_recipe);
            tvName = itemLayoutView.findViewById(R.id.tv_name);
            tvHeadline = itemLayoutView.findViewById(R.id.tv_headline);
            tvFat = itemLayoutView.findViewById(R.id.tv_fat);
            tvCalories = itemLayoutView.findViewById(R.id.tv_calories);

        }

        fun initialize(item: RecipeModel, action: OnRecipeItemClickListner) {
            tvName.text = item.name
            tvHeadline.text = item.headline
            tvCalories.text = ("Calories : ${item.calories}")
            tvFat.text = ("Fat : ${item.fats}")
            Glide.with(itemView.context).load(item.image).into(imgRecipe)

            itemView.setOnClickListener { action.onItemClick(item, adapterPosition) }
        }

    }

}

interface OnRecipeItemClickListner {
    fun onItemClick(item: RecipeModel, position: Int)
}