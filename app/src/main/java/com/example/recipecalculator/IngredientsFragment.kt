package com.example.recipecalculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IngredientsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchEditText: EditText
    private lateinit var addButton: Button
    private lateinit var adapter: IngredientAdapter

    private val ingredientDao by lazy {
        AppDatabase.getInstance(requireContext()).ingredientDao()
    }

    private var fullIngredientList: List<Ingredient> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ingredients, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.ingredient_recycle_view)
        searchEditText = view.findViewById(R.id.search_ingredients)
        addButton = view.findViewById(R.id.new_ingredient_btn)

        adapter = IngredientAdapter(
            onEditClick = { ingredient -> showEditIngredientDialog(ingredient) },
            onDeleteClick = { ingredient -> deleteIngredient(ingredient) }
        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        loadIngredients()

        setupSearch()
        setupAddButton()
    }

    private fun loadIngredients() {
        lifecycleScope.launch {
            fullIngredientList = withContext(Dispatchers.IO) {
                ingredientDao.getAll()
            }
            adapter.submitList(fullIngredientList)
        }
    }

    private fun setupSearch() {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterIngredients(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun filterIngredients(query: String) {
        val filtered = fullIngredientList.filter {
            it.name.contains(query, ignoreCase = true)
        }
        adapter.submitList(filtered)
    }

    private fun setupAddButton() {
        addButton.setOnClickListener {
            showAddIngredientDialog()
        }
    }

    private fun showAddIngredientDialog() {
        // You can use a DialogFragment here
        AddIngredientDialogFragment {
            // Callback when ingredient is added
            loadIngredients()
        }.show(parentFragmentManager, "AddIngredientDialog")
    }

    private fun showEditIngredientDialog(ingredient: Ingredient) {
        EditIngredientDialogFragment(ingredient) {
            // Callback when ingredient is edited
            loadIngredients()
        }.show(parentFragmentManager, "EditIngredientDialog")
    }

    private fun deleteIngredient(ingredient: Ingredient) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                ingredientDao.delete(ingredient)
            }
            loadIngredients()
        }
    }
}
