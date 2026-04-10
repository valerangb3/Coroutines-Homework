package otus.homework.coroutines.presentation

import android.graphics.Bitmap

data class CatFact(
    val fact: String,
    val image: Bitmap? = null
)
