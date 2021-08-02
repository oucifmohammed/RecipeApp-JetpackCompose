package com.example.recipeapp.presentation.ui.recipe_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.presentation.util.UserPreferencesManager
import com.example.recipeapp.repositories.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.ArrayList
import javax.inject.Inject
import javax.inject.Named


const val PAGE_SIZE = 30

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    @Named("auth_token") private val authToken: String,
    private val userPreferencesManager: UserPreferencesManager
): ViewModel(){

    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val query = mutableStateOf("")
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)
    var categoryScrollPosition = 0
    val loading = mutableStateOf(false)
    val page = mutableStateOf(1)
    var recipeListScrollPosition = 0

    private val userPreferencesFlow = userPreferencesManager.userPreferencesFlow

    val userPreferences = userPreferencesFlow.asLiveData()

    fun recipesSearch(){

        viewModelScope.launch (Dispatchers.IO){
            loading.value = true

            resetSearchState()
            delay(5000)
            val result = recipeRepository.search(
                token = authToken,
                page = 1,
                query = query.value
            )

            recipes.value = result

            loading.value = false
        }
    }

    private fun incrementPage(){
        page.value = page.value + 1
    }

    fun onChangeRecipeScrollPosition(position: Int){
        recipeListScrollPosition = position
    }

    //Append new recipes to the current list of recipes
    private fun appendRecipe(recipes: List<Recipe>){
        val current = ArrayList(this.recipes.value)

        current.addAll(recipes)
        this.recipes.value = current
    }

    fun nextPage(){
        viewModelScope.launch {

            //prevent duplicate events due to recompose happening too quickly
            if((recipeListScrollPosition + 1) >= page.value * PAGE_SIZE){
                loading.value = true
                incrementPage()

                if(page.value > 1){
                    val result = recipeRepository.search(
                        token = authToken,
                        page = page.value,
                        query = query.value
                    )

                    appendRecipe(result)
                }

                loading.value = false
            }
        }

    }
    fun onQueryChanged(newQuery: String){
        query.value = newQuery
    }

    fun onSelectedCategoryChanged(categoryValue: String){
        val category = getFoodCategory(categoryValue)
        selectedCategory.value = category
        onQueryChanged(categoryValue)
    }

    fun onChangeCategoryScrollPosition(position: Int){
       categoryScrollPosition = position
    }

    private fun resetSearchState(){
        recipes.value = listOf()
        page.value = 1
        onChangeRecipeScrollPosition(0)
        if(selectedCategory.value?.value != query.value)
            clearSelectedStrategy()
    }

    private fun clearSelectedStrategy(){
        selectedCategory.value = null
    }

    fun showDarkTheme(show: Boolean){
        viewModelScope.launch {
            userPreferencesManager.updateShowDarkTheme(show)
        }
    }
}