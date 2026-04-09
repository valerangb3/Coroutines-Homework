package otus.homework.coroutines

import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface CatsService {

    @GET("fact")
    suspend fun getCatFact() : Fact
}