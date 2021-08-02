package com.example.recipeapp.presentation.ui.recipe_list.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.recipeapp.presentation.ui.recipe_list.RecipeListViewModel
import com.example.recipeapp.presentation.ui.recipe_list.getAllFoodCategories
import kotlinx.coroutines.launch

@Composable
fun FoodCategoriesList(
    viewModel: RecipeListViewModel
) {

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, bottom = 8.dp),
        state = listState
    ) {
        coroutineScope.launch {
            listState.animateScrollToItem(viewModel.categoryScrollPosition)
        }

        itemsIndexed(items = getAllFoodCategories()){ index, category ->
            FoodCategoryChip(
                category = category.value,
                isSelected = viewModel.selectedCategory.value == category,
                onSelectedCategoryChanged = {
                    viewModel.onSelectedCategoryChanged(it)
                    viewModel.onChangeCategoryScrollPosition(index)
                },
                onExecuteSearch = {
                    viewModel.recipesSearch()
                }
            )
        }
    }
}