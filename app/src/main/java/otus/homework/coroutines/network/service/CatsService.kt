package otus.homework.coroutines.network.service

import otus.homework.coroutines.network.model.Fact
import retrofit2.http.GET

interface CatsService {

    @GET("fact")
    suspend fun getCatFact() : Fact
}