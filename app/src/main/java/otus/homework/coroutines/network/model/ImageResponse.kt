package otus.homework.coroutines.network.model

import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @field:SerializedName("id")
    val id: String,
    @field:SerializedName("url")
    val url: String,
    @field:SerializedName("width")
    val width: Int,
    @field:SerializedName("height")
    val height: Int
)
