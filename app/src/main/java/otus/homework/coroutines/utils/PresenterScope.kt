package otus.homework.coroutines.utils

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

private val exHandler = getCoroutineExceptionHandler()

fun getCoroutineExceptionHandler(onErrorHandler: ((ex: Throwable) -> Unit)? = null): CoroutineExceptionHandler {
    return CoroutineExceptionHandler { _, exception ->
        CrashMonitor.trackWarning()
        onErrorHandler?.invoke(exception)
    }
}

fun PresenterScope.cancel() = this.cancel()

class PresenterScope(
    context: CoroutineContext = Dispatchers.Main + CoroutineName("CatsCoroutine") + exHandler + SupervisorJob()
) :
    CoroutineScope {
    override val coroutineContext: CoroutineContext = context
}