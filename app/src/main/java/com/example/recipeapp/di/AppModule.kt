package com.example.recipeapp.di

import android.content.Context
import com.example.recipeapp.network.RecipeServiceApi
import com.example.recipeapp.network.model.RecipeDtoMapper
import com.example.recipeapp.presentation.util.UserPreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesRecipeMapper(): RecipeDtoMapper {
        return RecipeDtoMapper()
    }

    @Singleton
    @Provides
    fun provideRecipeServiceApi(): RecipeServiceApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://food2fork.ca/api/recipe/")
            .build()
            .create(RecipeServiceApi::class.java)
    }

    @Singleton
    @Provides
    @Named("auth_token")
    fun provideAuthToken(): String = "Token 9c8b06d329136da358c2d00e76946b0111ce2c48"

//    @Provides
//    @Singleton
//    fun provideUserPreferencesManager(@ApplicationContext context: Context) = UserPreferencesManager(context)
}