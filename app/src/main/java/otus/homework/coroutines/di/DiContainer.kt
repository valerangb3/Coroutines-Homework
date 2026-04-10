package otus.homework.coroutines.di

import otus.homework.coroutines.network.RetrofitClient
import otus.homework.coroutines.network.service.CatsService
import otus.homework.coroutines.network.service.ImgService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DiContainer {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://catfact.ninja/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val retrofitImg by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val service by lazy { retrofit.create(CatsService::class.java) }

    private val imgService by lazy { retrofitImg.create(ImgService::class.java) }

    val retrofitClient = RetrofitClient(
        catService = service,
        imgService = imgService,
    )
}