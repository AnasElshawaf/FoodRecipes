package com.develop.recipes.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.develop.recipes.BR
import com.develop.recipes.R
import com.develop.recipes.databinding.ActivityRecipeDetailsBinding
import com.develop.recipes.utils.NetworkConnection
import kotlinx.android.synthetic.main.activity_recipe_details.*
import kotlinx.android.synthetic.main.content_main.*

class RecipeDetailsActivity : AppCompatActivity() {

    lateinit var binding:ActivityRecipeDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_recipe_details)
        binding.setVariable(BR.recipe,intent.getSerializableExtra("recipe"))
        binding.executePendingBindings()

        checkNetWorkAvaliable()


    }

    private fun checkNetWorkAvaliable() {
        val networkConnection= NetworkConnection(applicationContext)
        networkConnection.observe(this, androidx.lifecycle.Observer {isConnected->
            if (isConnected){
               no_internet_textview.visibility=View.GONE
            }else{
                no_internet_textview.visibility=View.VISIBLE
            }
        })
    }

}
