package otus.homework.coroutines.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import otus.homework.coroutines.network.RetrofitClient
import otus.homework.coroutines.utils.getCoroutineExceptionHandler
import java.net.SocketTimeoutException

class CatsViewModel(
    private val retrofitClient: RetrofitClient,
) : ViewModel() {
    private var _state = MutableStateFlow<Result>(Idle)
    val state = _state.asStateFlow()

    fun loadContent() {
        val exHandler = getCoroutineExceptionHandler {
            Error("Probably request error")
        }
        viewModelScope.launch(exHandler) {
            try {
                val fact = retrofitClient.getCatFact()
                if (fact != null) _state.value = Success(fact)
                else _state.value = Error("Can`t get cat fact")
            } catch (ex: CancellationException) {
                throw ex
            } catch (_: SocketTimeoutException) {
                _state.value = Error("Не удалось получить ответ от сервером")
            } catch (ex: Exception) {
                _state.value = Error(ex.message ?: "Probably network error")
            }
        }
    }

    companion object {
        fun getFactory(retrofitClient: RetrofitClient): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                    return CatsViewModel(retrofitClient) as T
                }
            }
        }
    }
}