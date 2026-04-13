package otus.homework.coroutines.presentation

sealed interface Result<out T>
class Success<out T>(val data: T) : Result<T>
class Error(val errorMessage: String) : Result<Nothing>
object Idle : Result<Nothing>