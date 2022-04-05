package com.example.newsapplication

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.adapter.RecyclerAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://newsapi.org"

class MainActivity : AppCompatActivity() {

    lateinit var countdownTimer: CountDownTimer
    private var seconds = 3L

    private var titlesList = mutableListOf<String>()
    private var descList = mutableListOf<String>()
    private var imagesList = mutableListOf<String>()
    private var linksList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        makeAPIRequest()
    }


    private fun fadeIn() {
        val v_blackScreen:View=findViewById(R.id.v_blackScreen)
        v_blackScreen.animate().apply {
            alpha(0f)
            duration = 3000
        }.start()
    }


    private fun makeAPIRequest() {
        val progressBar:ProgressBar=findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getNews()

                for (article in response.articles) {
                    Log.d("MainActivity", "Result + $article")
                    addToList(article.title, article.description, article.urlToImage, article.url)
                }

                withContext(Dispatchers.Main) {
                    setUpRecyclerView()
                    fadeIn()
                    progressBar.visibility = View.GONE
                }
            } catch (e: Exception) {
                Log.d("MainActivity", e.toString())
                withContext(Dispatchers.Main) {
                    attemptRequestAgain(seconds)

                }
            }

        }
    }

    private fun attemptRequestAgain(seconds: Long) {
        val tv_noInternetCountDown:TextView=findViewById(R.id.tv_noInternetCountDown)
        countdownTimer = object: CountDownTimer(seconds*1010,1000){
            override fun onFinish() {
                makeAPIRequest()
                countdownTimer.cancel()
                tv_noInternetCountDown.visibility = View.GONE
                this@MainActivity.seconds+=3
            }
            override fun onTick(millisUntilFinished: Long) {
                tv_noInternetCountDown.visibility = View.VISIBLE
                tv_noInternetCountDown.text = "Cannot retrieve data...\nTrying again in: ${millisUntilFinished/1000}"
                Log.d("MainActivity", "Could not retrieve data. Trying again in ${millisUntilFinished/1000} seconds")
            }
        }
        countdownTimer.start()
    }

    private fun setUpRecyclerView() {
        val rv_recyclerView:RecyclerView=findViewById(R.id.rv_recyclerView)
        rv_recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        rv_recyclerView.adapter = RecyclerAdapter(titlesList, descList, imagesList, linksList)
    }

    private fun addToList(title: String, description: String, image: String, link: String) {
        linksList.add(link)
        titlesList.add(title)
        descList.add(description)
        imagesList.add(image)
    }
}