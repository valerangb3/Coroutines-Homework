package otus.homework.coroutines.utils

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

private val exHandler = getCoroutineExceptionHandler()

fun getCoroutineExceptionHandler(onErrorHandler: ((ex: Throwable) -> Unit)? = null): CoroutineExceptionHandler {
    return CoroutineExceptionHandler { _, exception ->
        CrashMonitor.trackWarning()
        onErrorHandler?.invoke(exception)
    }
}

class PresenterScope(
    context: CoroutineContext = Dispatchers.Main + CoroutineName("CatsCoroutine") + exHandler
) :
    CoroutineScope {
    override val coroutineContext: CoroutineContext = context

    fun cancel() {
        this.cancel()
    }
}