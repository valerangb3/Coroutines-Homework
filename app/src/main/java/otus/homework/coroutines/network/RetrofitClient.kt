package otus.homework.coroutines.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import otus.homework.coroutines.network.service.CatsService
import otus.homework.coroutines.network.service.ImgService
import otus.homework.coroutines.presentation.CatFact

class RetrofitClient(
    private val catService: CatsService,
    private val imgService: ImgService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getCatFact(): CatFact {
        return withContext(dispatcher) {
            val catFactDeferred = async {
                catService.getCatFact()
            }
            val imageDeferred = async {
                imgService.getImage()
            }
            val catFact = catFactDeferred.await()
            val imageResponse = imageDeferred.await()
            CatFact(
                fact = catFact.fact,
                image = imageResponse[0].url
            )
        }
    }
}