package otus.homework.coroutines.presentation

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import otus.homework.coroutines.ICatsView
import otus.homework.coroutines.network.RetrofitClient

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
                    _catsView?.populate(fact)
                }
            } catch (ex: CancellationException) {
                throw ex
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