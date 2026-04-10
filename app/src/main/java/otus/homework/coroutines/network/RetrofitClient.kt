package otus.homework.coroutines.network

import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import otus.homework.coroutines.network.service.CatsService
import otus.homework.coroutines.network.service.ImgService
import otus.homework.coroutines.presentation.CatFact
import otus.homework.coroutines.presentation.loadImg

class RetrofitClient(
    private val catService: CatsService,
    private val imgService: ImgService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getCatFact(): CatFact? {
        return try {
            withContext(dispatcher) {
                val catFactDeferred = async {
                    catService.getCatFact()
                }
                val imageDeferred = async {
                    imgService.getImage()
                }
                val catFact = catFactDeferred.await()
                val imageResponse = imageDeferred.await()
                val bitmapDeferred = async {
                    Picasso.get().loadImg(imageResponse[0].url)
                }
                val bitmap = bitmapDeferred.await()
                CatFact(
                    fact = catFact.fact,
                    image = bitmap
                )
            }
        } catch (ex: Exception) { throw ex }
    }
}