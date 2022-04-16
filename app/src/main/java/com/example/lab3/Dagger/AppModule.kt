package com.example.lab3.Dagger

import com.example.lab3.FilmsApi
import com.example.lab3.Services.FilmService
import dagger.Module
import dagger.Provides

@Module
object AppModule {
    @Provides
    fun getFilmAPI(): FilmsApi {
        return FilmService().filmsApi
    }
}