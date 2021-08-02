package com.example.recipeapp.presentation.ui.recipe_list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.recipeapp.presentation.ui.recipe_list.RecipeListViewModel
import com.example.recipeapp.R

@ExperimentalComposeUiApi
@Composable
fun SearchToolbar(
    viewModel: RecipeListViewModel,
    darkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colors.surface,
        elevation = 8.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row {
                val keyBoardController = LocalSoftwareKeyboardController.current
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(8.dp),
                    value = viewModel.query.value,
                    onValueChange = { newValue ->
                        viewModel.onQueryChanged(newValue)
                    },
                    label = {
                        Text(text = "Search")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    leadingIcon = {
                        Icon(Icons.Filled.Search, contentDescription = "search icon")
                    },
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            viewModel.recipesSearch()
                            keyBoardController?.hide()
                        }
                    ),
                    textStyle = TextStyle(
                        color = MaterialTheme.colors.onSurface,
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.surface
                    )
                )

                IconButton(modifier = Modifier.align(CenterVertically), onClick = onToggleTheme) {
                    Icon(Icons.Filled.MoreVert, contentDescription = "")
                    Icon(painter = painterResource(id = if(darkTheme) R.drawable.moon else R.drawable.sun), contentDescription = "")
                }
            }

            FoodCategoriesList(viewModel)
        }

    }
}