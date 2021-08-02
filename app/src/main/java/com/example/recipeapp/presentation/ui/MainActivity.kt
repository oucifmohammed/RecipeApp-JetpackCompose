package com.example.recipeapp.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.recipeapp.presentation.ui.recipe.RecipeDetails
import com.example.recipeapp.presentation.ui.recipe_list.RecipeListViewModel
import com.example.recipeapp.presentation.ui.recipe_list.RecipesListScreen
import com.example.recipeapp.presentation.ui.theme.RecipeAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val recipeListViewModel: RecipeListViewModel by viewModels()

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{

            val darkTheme: MutableState<Boolean?> = mutableStateOf(null)

            recipeListViewModel.userPreferences.observe(this){ preferences ->
                darkTheme.value = preferences.darkTheme
            }
            RecipeAppTheme(
                darkTheme = darkTheme.value ?: false
            ) {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "recipes_list"){

                    composable("recipes_list") {
                        RecipesListScreen(navController,darkTheme = darkTheme.value ?: false)
                    }

                    composable(
                        "recipe_details/{recipeId}",
                        arguments = listOf(
                            navArgument("recipeId"){
                                type = NavType.IntType
                            }
                        )
                    ){
                        val recipeId = it.arguments?.getInt("recipeId")

                        recipeId?.let { id ->
                            RecipeDetails(id)
                        }
                    }
                }
            }
        }
    }
}
