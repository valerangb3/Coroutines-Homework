package otus.homework.coroutines

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsPresenter(
    private val catsService: CatsService,
    private val imageService: ImgService,
    private val coroutineScope: CoroutineScope = CoroutineScope( Dispatchers.Main + CoroutineName("CatsCoroutine")),
    private val onErrorRequest: ((ex: Exception) -> Unit)? = {}
) {

    private var _catsView: ICatsView? = null

    fun onInitComplete(dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        coroutineScope.launch {
            try {
                withContext(dispatcher) {
                    val catFactDeferred = async {
                        catsService.getCatFact()
                    }
                    val imageDeferred = async {
                        imageService.getImage()
                    }
                    val catFact = catFactDeferred.await()
                    val image = imageDeferred.await()
                    _catsView?.populate(catFact)
                }
            } catch (ex: Exception) {
                Log.d("EXCEPTION", ex.message ?: "SMTH ERR")
                onErrorRequest?.invoke(ex)
            }
        }
    }

    fun attachView(catsView: ICatsView) {
        _catsView = catsView
    }

    fun detachView() {
        _catsView = null
    }
}