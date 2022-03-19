package com.example.lab3.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Films {
    @SerializedName("docs")
    @Expose
    var films: List<FilmInfo>? = null
}

class FilmInfo {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("year")
    @Expose
    var year: Int? = null

    @SerializedName("rating")
    @Expose
    var rating: RatingInfo? = null

    @SerializedName("movieLength")
    @Expose
    var duration: Int? = null

    @SerializedName("genres")
    @Expose
    var genres: List<NameString>? = null

    @SerializedName("countries")
    @Expose
    var countries: List<NameString>? = null

    @SerializedName("poster")
    @Expose
    var poster: PosterInfo? = null
}

class RatingInfo {
    @SerializedName("kp")
    @Expose
    var kinopoisk: Float? = null
}

class NameString {
    @SerializedName("name")
    @Expose
    var name: String? = null
}

class PosterInfo {
    @SerializedName("previewUrl")
    @Expose
    var url: String? = null
}