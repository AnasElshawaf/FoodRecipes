package com.develop.recipes.ui.base

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.develop.recipes.ui.main.viewModel.RecipeViewModel

class RecipeViewModelFactory(private val context: Context) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RecipeViewModel(context) as T
    }

}
