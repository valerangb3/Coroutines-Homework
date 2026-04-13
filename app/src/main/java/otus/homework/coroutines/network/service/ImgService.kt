package otus.homework.coroutines.network.service

import otus.homework.coroutines.network.model.ImageResponse
import retrofit2.http.GET

interface ImgService {

    @GET("images/search")
    suspend fun getImage(): List<ImageResponse>
}