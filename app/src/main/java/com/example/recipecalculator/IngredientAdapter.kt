package com.example.recipecalculator

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class IngredientAdapter(
    private val onEditClick: (Ingredient) -> Unit,
    private val onDeleteClick: (Ingredient) -> Unit
) : ListAdapter<Ingredient, IngredientAdapter.ViewHolder>(IngredientDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ingredient, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.imageViewIngredient)
        private val nameText: TextView = view.findViewById(R.id.textName)
        private val detailsText: TextView = view.findViewById(R.id.textDetails)
        private val buttonEdit: ImageButton = view.findViewById(R.id.buttonEdit)
        private val buttonDelete: ImageButton = view.findViewById(R.id.buttonDelete)

        @SuppressLint("SetTextI18n")
        fun bind(ingredient: Ingredient) {
            nameText.text = ingredient.name
            detailsText.text = "${ingredient.unitType} | ${"%.2f".format(ingredient.pricePerUnit)} DZD"

            if (ingredient.image != 0) {
                imageView.setImageResource(ingredient.image)
            } else {
                imageView.setImageResource(R.drawable.eggs)
            }

            buttonEdit.setOnClickListener {
                onEditClick(ingredient)
            }

            buttonDelete.setOnClickListener {
                onDeleteClick(ingredient)
            }
        }
    }
}

