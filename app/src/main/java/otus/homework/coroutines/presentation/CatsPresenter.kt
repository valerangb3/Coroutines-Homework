package otus.homework.coroutines.presentation

import android.util.Log
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import otus.homework.coroutines.ICatsView
import otus.homework.coroutines.network.RetrofitClient
import otus.homework.coroutines.network.service.CatsService
import otus.homework.coroutines.network.service.ImgService
import kotlin.invoke

class CatsPresenter(
    private val retrofitClient: RetrofitClient,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main + CoroutineName("CatsCoroutine")),
    private val onErrorRequest: ((ex: Exception) -> Unit)? = {}
) {
    private var _catsView: ICatsView? = null

    fun onInitComplete() {
        coroutineScope.launch {
            try {
                val catFact = retrofitClient.getCatFact()
                catFact?.let { fact ->
                    _catsView?.populate(catFact)
                }
            } catch (ex: Exception) {
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