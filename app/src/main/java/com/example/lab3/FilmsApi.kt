package com.example.lab3

import com.example.lab3.Models.FilmInfo
import com.example.lab3.Models.Films
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FilmsApi {
    @GET("/movie")
    fun getFilms(
        @Query("token") token: String,
        @Query("field") field: String,
        @Query("search") search: String,
        @Query("field") fieldAdd: String,
        @Query("search") searchAdd: String,
        @Query("sortField") sortField: String,
        @Query("sortType") sortType: String,
        @Query("limit") limit: Int,
    ): Call<Films>

    @GET("/movie")
    fun getFilm(
        @Query("token") token: String,
        @Query("field") field: String,
        @Query("search") search: String,
    ): Call<FilmInfo>
}