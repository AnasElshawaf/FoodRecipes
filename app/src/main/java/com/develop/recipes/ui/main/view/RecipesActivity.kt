package com.develop.recipes.ui.main.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.cooltechworks.views.shimmer.ShimmerRecyclerView
import com.develop.recipes.R
import com.develop.recipes.data.model.RecipeModel
import com.develop.recipes.ui.base.RecipeViewModelFactory
import com.develop.recipes.ui.main.adapter.OnRecipeItemClickListner
import com.develop.recipes.ui.main.adapter.RecipeAdapter
import com.develop.recipes.ui.main.viewModel.RecipeViewModel
import com.develop.recipes.utils.NetworkConnection
import kotlinx.android.synthetic.main.content_main.*
import java.util.*
import kotlin.collections.ArrayList

class RecipesActivity : AppCompatActivity(), View.OnClickListener, OnRecipeItemClickListner {

    private lateinit var networkConnection: NetworkConnection
    var dataList = ArrayList<RecipeModel>()
    var displayList = ArrayList<RecipeModel>()

    lateinit var rvRecipes: ShimmerRecyclerView
    lateinit var adapter: RecipeAdapter
    lateinit var viewmodel: RecipeViewModel

    lateinit var pref: SharedPreferences
    var mSorting: String = "none"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_main)

        pref = getSharedPreferences("my_pref", Context.MODE_PRIVATE)
        mSorting = pref.getString("sort", "none").toString()

        checkNetWorkAvaliable()
        initViews();
        setUpRecyclerView()
        setUpViewModel()

    }

    private fun checkNetWorkAvaliable() {
         networkConnection=NetworkConnection(applicationContext)
        networkConnection.observe(this, androidx.lifecycle.Observer {isConnected->
            if (isConnected){
                ly_search.visibility=View.VISIBLE
                rvRecipes.visibility=View.VISIBLE
                ly_no_netwoork.visibility=View.GONE
            }else{
                ly_search.visibility=View.GONE
                rvRecipes.visibility=View.GONE
                ly_no_netwoork.visibility=View.VISIBLE
            }
        })
    }

    private fun setUpRecyclerView() {
        //setting up Recyclerview Recipes
        adapter = RecipeAdapter(displayList, this, this)
        rvRecipes.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvRecipes.adapter = adapter
        rvRecipes.showShimmerAdapter()
    }

    private fun initViews() {
        rvRecipes = findViewById(R.id.rv_recipes)
        img_search.setOnClickListener(this)
        bt_calories.setOnClickListener(this)
        bt_fat.setOnClickListener(this)
    }

    private fun setUpViewModel() {
        viewmodel =
            ViewModelProvider(this, RecipeViewModelFactory(this)).get(RecipeViewModel::class.java)
        viewmodel.getData()!!.observe(this, androidx.lifecycle.Observer {
            rvRecipes.hideShimmerAdapter()
            dataList.addAll(it)
            displayList.addAll(dataList)
            checkIfSortingCached()
            rvRecipes.adapter!!.notifyDataSetChanged()

        })

    }

    private fun checkIfSortingCached() {
            when (mSorting) {
                "fat" -> sortByFat(adapter)
                "calories" -> sortByCalories(adapter)
            }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.img_search -> {
                if (tv_search.text.isNotEmpty()) {
                    filterRecipesList(tv_search.text)
                } else {
                    displayList.clear()
                    displayList.addAll(dataList)
                    rvRecipes.adapter!!.notifyDataSetChanged()
                }
            }

            R.id.bt_calories -> {
                val editor: SharedPreferences.Editor = pref.edit()
                editor.putString("sort", "calories").apply()
                sortByCalories(adapter)
            }

            R.id.bt_fat -> {
                val editor: SharedPreferences.Editor = pref.edit()
                editor.putString("sort", "fat").apply()
                 sortByFat(adapter)
            }
        }
    }

    private fun sortByFat(myadapter: RecipeAdapter) {
        bt_fat.setBackgroundResource(R.drawable.background_border_red)
        bt_calories.setBackgroundResource(R.drawable.background_gray_with_border)
        displayList.sortWith(compareBy { it.fats.split("\\s".toRegex())[0].toIntOrNull() })
        myadapter.notifyDataSetChanged()
    }

    private fun sortByCalories(myadapter: RecipeAdapter) {
        bt_calories.setBackgroundResource(R.drawable.background_border_red)
        bt_fat.setBackgroundResource(R.drawable.background_gray_with_border)
        displayList.sortWith(compareBy { it.calories.split("\\s".toRegex())[0].toIntOrNull() })
        myadapter.notifyDataSetChanged()
    }

    private fun filterRecipesList(text: Editable?) {
        displayList.clear()
        var search = text.toString().toLowerCase(Locale.getDefault())
        dataList.forEach {
            if (it.name.toLowerCase(Locale.getDefault()).startsWith(search)) {
                displayList.add(it)
            }
        }
        rvRecipes.adapter!!.notifyDataSetChanged()
    }

    override fun onItemClick(item: RecipeModel, position: Int) {
        val intent = Intent(this, RecipeDetailsActivity::class.java)
        intent.putExtra("recipe", item)
        startActivity(intent)
    }

    override fun onPause() {
        super.onPause()
        networkConnection.stopCheckNetwork()
    }
}
