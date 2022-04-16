package com.example.lab3

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.ArrayAdapter
import com.example.lab3.Dagger.AppComponent
import com.example.lab3.Dagger.DaggerAppComponent
import com.example.lab3.Models.FilmInfo
import com.example.lab3.Models.Films
import com.example.lab3.Services.FilmService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var films: List<FilmInfo>
    lateinit var appComponent: AppComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appComponent = DaggerAppComponent.create()

        setContentView(R.layout.activity_list)
        getFilms()
    }

    private fun getFilms() {
        listView = findViewById(R.id.films_list)

        listView.setOnItemClickListener { _, _, position, _ ->
            Log.i("itemClick", "position = $position")

            val filmInfo = films[position]

            val infoIntent = Intent(this, InfoActivity::class.java)
            infoIntent.putExtra("FILM_ID", filmInfo.id)
            startActivity(infoIntent)
        }

        val context: Context = this

        val intent = intent
        val isPremium = intent.getBooleanExtra("USER_PREMIUM", false)

        var filmLimit = 5

        if (isPremium)
            filmLimit = 30

        val callListFilms = appComponent.getFilmsAPI().getFilms(
            token = getString(R.string.api_token),
            field = "year",
            search = "2022",
            fieldAdd = "typeNumber",
            searchAdd = "1",
            sortField = "votes.kp",
            sortType = "-1",
            limit = filmLimit,
        )
        val callback = object : Callback<Films> {
            override fun onResponse(call: Call<Films>, response: Response<Films>) {
                val filmList: Films? = response.body()
                films = filmList?.films!!

                val filmListResult: MutableList<String> = mutableListOf()

                films.forEach {
                    filmListResult.add("${it.name} (${it.rating?.kinopoisk})")
                }

                val adapter: ArrayAdapter<String?> = ArrayAdapter(
                    context,
                    android.R.layout.simple_list_item_1,
                    filmListResult as List<String?>,
                )

                listView.adapter = adapter
            }

            override fun onFailure(call: Call<Films>, t: Throwable) {
                Log.w("Something went wrong", t.message.toString())
            }
        }

        callListFilms?.enqueue(callback)
    }
}