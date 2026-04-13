package otus.homework.coroutines.presentation

import android.graphics.Bitmap
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun Picasso.loadImg(url: String, dispatcher: CoroutineDispatcher = Dispatchers.IO): Bitmap? {
    val bitmap = withContext(dispatcher) {
        this@loadImg
            .load(url)
            .get()
    }
    return bitmap
}