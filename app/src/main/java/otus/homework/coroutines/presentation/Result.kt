package otus.homework.coroutines.presentation

sealed interface Result
class Success(val catFact: CatFact) : Result
class Error(val errorMessage: String) : Result