package com.example.lab3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.lab3.Dagger.AppComponent
import com.example.lab3.Dagger.DaggerAppComponent
import com.example.lab3.Models.FilmInfo
import com.example.lab3.Services.FilmService
import dagger.android.DaggerApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InfoActivity : AppCompatActivity() {
    lateinit var filmNameView: TextView
    lateinit var filmRatingView: TextView
    lateinit var filmYearGenreView: TextView
    lateinit var filmCountryDurationView: TextView
    lateinit var filmDescriptionView: TextView
    lateinit var filmPoster: ImageView
    lateinit var appComponent: AppComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        filmNameView = findViewById(R.id.film_name)
        filmRatingView = findViewById(R.id.film_rating)
        filmYearGenreView = findViewById(R.id.film_year_genre)
        filmCountryDurationView = findViewById(R.id.film_country_duration)
        filmDescriptionView = findViewById(R.id.film_description)
        filmPoster = findViewById(R.id.film_poster)

        appComponent = DaggerAppComponent.create()

        val intent = intent
        val filmId = intent.getIntExtra("FILM_ID", 0)

        getFilmInfo(filmId)
    }

    private fun getFilmInfo(filmId: Int) {
        val callFilmInfo = appComponent.getFilmsAPI().getFilm(
            token = getString(R.string.api_token),
            field = "id",
            search = filmId.toString(),
        )
        val callback = object : Callback<FilmInfo> {
            override fun onResponse(call: Call<FilmInfo>, response: Response<FilmInfo>) {
                val filmInfo: FilmInfo? = response.body()

                filmNameView.text = filmInfo?.name
                filmRatingView.text = filmInfo?.rating?.kinopoisk.toString()
                filmYearGenreView.text = "${filmInfo?.year}, ${filmInfo?.genres?.get(0)?.name}"
                filmCountryDurationView.text = "${filmInfo?.countries?.get(0)?.name}"
                filmDescriptionView.text = filmInfo?.description

                Glide.with(applicationContext)
                    .load(filmInfo?.poster?.url)
                    .into(filmPoster)
            }

            override fun onFailure(call: Call<FilmInfo>, t: Throwable) {
                Log.w("Something went wrong", t.message.toString())
            }
        }

        callFilmInfo.enqueue(callback)
    }
}