package otus.homework.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import otus.homework.coroutines.di.DiContainer
import otus.homework.coroutines.presentation.CatsPresenter
import otus.homework.coroutines.presentation.CatsViewModel
import otus.homework.coroutines.presentation.Error
import otus.homework.coroutines.presentation.Idle
import otus.homework.coroutines.presentation.Success
import otus.homework.coroutines.utils.CrashMonitor
import otus.homework.coroutines.utils.PresenterScope
import java.net.SocketTimeoutException

class MainActivity : AppCompatActivity() {

    lateinit var catsPresenter: CatsPresenter

    lateinit var scope: PresenterScope

    private val catsViewModel by viewModels<CatsViewModel> {
        CatsViewModel.getFactory(diContainer.retrofitClient)
    }

    private val diContainer = DiContainer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        withViewModel(view)
        //withPresenter(view)
    }

    private fun withPresenter(catsView: CatsView) {
        val appContext = this.applicationContext
        scope = PresenterScope()
        catsPresenter = CatsPresenter(
            retrofitClient = diContainer.retrofitClient,
            coroutineScope = scope,
            onErrorRequest = { exception ->
                if (exception is SocketTimeoutException) {
                    Toast.makeText(
                        appContext,
                        "Не удалось получить ответ от сервера",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    CrashMonitor.trackWarning()
                    Toast.makeText(appContext, exception.message, Toast.LENGTH_SHORT).show()
                }
            }
        )
        catsView.refreshHandler = {
            catsPresenter.onInitComplete()
        }
        catsPresenter.attachView(catsView)
        catsPresenter.onInitComplete()
    }

    private fun withViewModel(catsView: CatsView) {
        val appContext = this.applicationContext
        catsView.refreshHandler = {
            catsViewModel.loadContent()
        }
        catsViewModel.loadContent()

        lifecycleScope.launch {
            catsViewModel.state.collect { result ->
                when (result) {
                    is Idle -> {}
                    is Success -> {
                        val fact = result.catFact
                        catsView.populate(fact)
                    }
                    is Error -> {
                        Toast.makeText(appContext, result.errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onStop() {
        /*scope.cancel()
        if (isFinishing) {
            catsPresenter.detachView()
        }*/
        super.onStop()
    }
}