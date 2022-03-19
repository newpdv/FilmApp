package com.example.lab3.Services

import com.example.lab3.FilmsApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FilmService {
    private val mRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            GsonConverterFactory.create()
        ).build()

    val filmsApi: FilmsApi
        get() = mRetrofit.create(FilmsApi::class.java)

    companion object {
        private var mInstance: FilmService? = null
        private const val BASE_URL = "https://api.kinopoisk.dev/"
        val instance: FilmService?
            get() {
                if (mInstance == null) {
                    mInstance = FilmService()
                }
                return mInstance;
            }
    }
}