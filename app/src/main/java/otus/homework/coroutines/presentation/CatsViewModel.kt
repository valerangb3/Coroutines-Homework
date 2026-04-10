package otus.homework.coroutines.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import otus.homework.coroutines.network.RetrofitClient
import kotlin.coroutines.CoroutineContext

class CatsViewModel(
    private val retrofitClient: RetrofitClient,
    private val coroutineContext: CoroutineContext
) : ViewModel() {

    fun loadContent(): Result {
        return viewModelScope.launch() {
            try {
                val fact = retrofitClient.getCatFact()
                Result
            } catch (ex: Exception) {
                Success
            }
        }
    }
}