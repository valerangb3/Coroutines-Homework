package otus.homework.coroutines

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class PresenterScope(context: CoroutineContext = Dispatchers.Main + CoroutineName("CatsCoroutine")) : CoroutineScope {
    override val coroutineContext: CoroutineContext = context

    fun cancel() {
        this.cancel()
    }
}