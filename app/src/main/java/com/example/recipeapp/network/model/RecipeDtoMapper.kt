package com.example.recipeapp.network.model

import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.domain.util.DomainMapper

class RecipeDtoMapper : DomainMapper<RecipeDto, Recipe> {
    override fun mapToDomainModel(model: RecipeDto): Recipe {
        return Recipe(
            id = model.pk,
            title = model.title,
            publisher = model.publisher,
            featuredImage = model.featuredImage,
            rating = model.rating,
            sourceUrl = model.sourceUrl,
            description = model.description,
            cookingInstruction = model.cookingInstruction,
            ingredients = model.ingredients ?: listOf(),
            dateAdded = model.dateAdded,
            dateUpdated = model.dateUpdated
        )
    }

    override fun mapFromDomainModel(domainModel: Recipe): RecipeDto {
        return RecipeDto(
            pk = domainModel.id,
            title = domainModel.title,
            publisher = domainModel.publisher,
            featuredImage = domainModel.featuredImage,
            rating = domainModel.rating,
            sourceUrl = domainModel.sourceUrl,
            description = domainModel.description,
            cookingInstruction = domainModel.cookingInstruction,
            ingredients = domainModel.ingredients ?: listOf(),
            dateAdded = domainModel.dateAdded,
            dateUpdated = domainModel.dateUpdated
        )
    }

    fun toDomainList(initial: List<RecipeDto>): List<Recipe>{
        return initial.map {
            mapToDomainModel(it)
        }
    }

    fun fromDomainModelList(initial: List<Recipe>): List<RecipeDto>{
        return initial.map {
            mapFromDomainModel(it)
        }
    }
}