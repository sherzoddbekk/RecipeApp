package com.example.recipeapp.edit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.recipeapp.ui.theme.RecipeAppTheme

class EditActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = intent.extras?.getString("id") ?: ""
        val title = intent.extras?.getString("title") ?: ""
        val description = intent.extras?.getString("description") ?: ""

        setContent {
            RecipeAppTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    EditView(id, title, description)
                }
            }
        }
    }
}