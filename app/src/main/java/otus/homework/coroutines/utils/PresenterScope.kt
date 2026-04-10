package otus.homework.coroutines.utils

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

private const val TAG_ERROR = "CATS_ERROR"

private val exHandler = CoroutineExceptionHandler { coroutineContext, exception ->
    CrashMonitor.trackWarning()
    Log.e(TAG_ERROR, "${exception.message}")
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