package com.develop.recipes.data.api

import com.develop.recipes.data.model.RecipeModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("recipes.json")
    fun getRecipes(): Call<List<RecipeModel>>

}