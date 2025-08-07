package com.example.recipecalculator

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddIngredientDialogFragment(
    private val onIngredientAdded: () -> Unit
) : DialogFragment() {

    private val imageOptions = listOf(
        R.drawable.eggs,
        R.drawable.milk_carton,
        R.drawable.butter,
        R.drawable.flour,
        R.drawable.flour_wheat,
        R.drawable.sugar_cube,
        R.drawable.honey,
        R.drawable.vanila,
        R.drawable.cooking_oil
    )


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_add_ingredient, null)

        val editName = view.findViewById<EditText>(R.id.edit_name)
        val spinner = view.findViewById<Spinner>(R.id.spinner_unit_type)
        val editPrice = view.findViewById<EditText>(R.id.edit_price)
        val buttonAdd = view.findViewById<Button>(R.id.button_add)
        val imageButton = view.findViewById<ImageButton>(R.id.image_button)

        var selectedImageId = R.drawable.eggs

        val unitOptions = listOf("1", "1Kg", "1g", "1L", "mL")
        spinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, unitOptions)

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Add Ingredient")
            .setView(view)
            .create()

        imageButton.setOnClickListener {
            showImagePicker { selected ->
                selectedImageId = selected
                imageButton.setImageResource(selected)
            }
        }

        buttonAdd.setOnClickListener {
            val name = editName.text.toString().trim()
            val unit = spinner.selectedItem.toString()
            val price = editPrice.text.toString().toDoubleOrNull()

            if (name.isEmpty() || price == null) {
                Toast.makeText(context, "Please fill all fields correctly", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newIngredient = Ingredient(
                name = name,
                unitType = unit,
                pricePerUnit = price,
                image = selectedImageId
            )

            lifecycleScope.launch {
                val dao = AppDatabase.getInstance(requireContext()).ingredientDao()
                withContext(Dispatchers.IO) {
                    dao.insert(newIngredient)
                }
                onIngredientAdded()
                dialog.dismiss()
            }
        }

        return dialog
    }

    private fun showImagePicker(onSelect: (Int) -> Unit) {
        val gridView = GridView(requireContext()).apply {
            numColumns = 3
            adapter = object : BaseAdapter() {
                override fun getCount() = imageOptions.size
                override fun getItem(position: Int) = imageOptions[position]
                override fun getItemId(position: Int) = position.toLong()

                override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                    val imageView = ImageView(requireContext())
                    imageView.setImageResource(imageOptions[position])
                    imageView.layoutParams = AbsListView.LayoutParams(200, 200)
                    imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                    return imageView
                }
            }
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Choose Image")
            .setView(gridView)
            .setNegativeButton("Cancel", null)
            .create()
            .also { dialog ->
                gridView.setOnItemClickListener { _, _, position, _ ->
                    val selectedImage = imageOptions[position]
                    onSelect(selectedImage)
                    dialog.dismiss()
                }
            }.show()
    }

}
