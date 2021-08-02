package com.example.recipeapp.di

import com.example.recipeapp.repositories.RecipeRepository
import com.example.recipeapp.repositories.RecipeRepository_impl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideRecipeRepository(impl: RecipeRepository_impl): RecipeRepository
}