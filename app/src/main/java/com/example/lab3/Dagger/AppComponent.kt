package com.example.lab3.Dagger

import com.example.lab3.FilmsApi
import com.example.lab3.Services.FilmService
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {
    fun getFilmsAPI(): FilmsApi
}