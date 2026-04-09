package otus.homework.coroutines

import retrofit2.http.GET

interface ImgService {

    @GET
    fun getImage(): Image
}