package com.example.recipeapp.presentation.ui.recipe

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.recipeapp.R
import com.example.recipeapp.presentation.ui.ScreenType
import com.example.recipeapp.presentation.ui.recipe_list.animation.LoadingRecipeListShimmer
import com.example.recipeapp.presentation.ui.recipe_list.components.CircularProgressBar

@Composable
fun RecipeDetails(
    recipeId: Int,
    viewModel: RecipeDetailsViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val recipe = viewModel.recipe.value
    val loading = viewModel.loading.value

    viewModel.getRecipe(recipeId)

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            recipe?.let { recipe ->
                Image(
                    painter = rememberImagePainter(
                        request = ImageRequest
                            .Builder(LocalContext.current)
                            .placeholder(R.drawable.empty_plate)
                            .data(recipe.featuredImage)
                            .build()
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(225.dp),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    ) {
                        recipe.title?.let { title ->
                            Text(
                                text = title,
                                modifier = Modifier
                                    .fillMaxWidth(0.85f)
                                    .wrapContentWidth(Alignment.Start),
                                style = MaterialTheme.typography.h3
                            )
                        }

                        recipe.rating?.let { rating ->
                            Text(
                                text = rating.toString(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.End)
                                    .align(Alignment.CenterVertically),
                                style = MaterialTheme.typography.h5
                            )
                        }
                    }

                    recipe.publisher?.let { publisher ->
                        val updated = recipe.dateUpdated

                        Text(
                            text = if(updated == null){
                                "Updated $updated by $publisher"
                            }else {
                                "By $publisher"
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            style = MaterialTheme.typography.caption
                        )
                    }

                    recipe.ingredients?.let { ingredients ->
                        for(ingredient in ingredients){
                            Text(
                                text = ingredient,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                style = MaterialTheme.typography.body1
                            )
                        }
                    }
                }
            } ?: LoadingRecipeListShimmer(imageHeight = 225.dp, screenType = ScreenType.RECIPEDETAILS)
        }
    }

}