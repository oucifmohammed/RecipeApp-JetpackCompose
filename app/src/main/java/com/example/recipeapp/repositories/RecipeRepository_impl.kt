package com.example.recipeapp.repositories

import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.network.RecipeServiceApi
import com.example.recipeapp.network.model.RecipeDtoMapper
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class RecipeRepository_impl @Inject constructor(
    private val recipeService: RecipeServiceApi,
    private val mapper: RecipeDtoMapper
) : RecipeRepository {

    override suspend fun search(token: String, page: Int, query: String): List<Recipe> {
        val result = recipeService.search(token, page, query)

        return mapper.toDomainList(result.recipes)
    }

    override suspend fun get(token: String, id: Int): Recipe {
        val result = recipeService.get(token, id)

        return mapper.mapToDomainModel(result)
    }
}