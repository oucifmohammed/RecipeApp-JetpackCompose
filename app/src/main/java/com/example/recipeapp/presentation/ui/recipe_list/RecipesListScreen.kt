package com.example.recipeapp.presentation.ui.recipe_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.recipeapp.presentation.ui.ScreenType
import com.example.recipeapp.presentation.ui.ScreenType.RECIPELIST
import com.example.recipeapp.presentation.ui.recipe_list.animation.LoadingRecipeListShimmer
import com.example.recipeapp.presentation.ui.recipe_list.components.CircularProgressBar
import com.example.recipeapp.presentation.ui.recipe_list.components.RecipeCard
import com.example.recipeapp.presentation.ui.recipe_list.components.SearchToolbar
import com.example.recipeapp.presentation.util.IMAGE_HEIGHT

@ExperimentalComposeUiApi
@Composable
fun RecipesListScreen(
    navController: NavController,
    viewModel: RecipeListViewModel = hiltViewModel(),
    darkTheme: Boolean
) {
    val loadingState = viewModel.loading.value
    val recipesList = viewModel.recipes.value
    val page = viewModel.page.value

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            SearchToolbar(viewModel = viewModel,darkTheme = darkTheme) {
                viewModel.showDarkTheme(!darkTheme)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    itemsIndexed(items = recipesList) { index, recipe ->

                        viewModel.onChangeRecipeScrollPosition(index)

                        if((index+1) >= (page * PAGE_SIZE) && !loadingState)
                            viewModel.nextPage()

                        RecipeCard(
                            recipe = recipe,
                            onClick = {
                                navController.navigate("recipe_details/${recipe.id}")
                            }
                        )
                    }
                }

                if(loadingState && recipesList.isEmpty())
                    LoadingRecipeListShimmer(imageHeight = IMAGE_HEIGHT.dp,screenType = RECIPELIST)

                if(loadingState && recipesList.isNotEmpty())
                    CircularProgressBar(isDisplayed = loadingState)
            }

        }

    }
}

