package otus.homework.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.coroutineScope
import otus.homework.coroutines.di.DiContainer
import otus.homework.coroutines.presentation.CatsPresenter
import otus.homework.coroutines.utils.CrashMonitor
import otus.homework.coroutines.utils.PresenterScope
import java.net.SocketTimeoutException

class MainActivity : AppCompatActivity() {

    lateinit var catsPresenter: CatsPresenter

    lateinit var scope: PresenterScope

    private val diContainer = DiContainer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)
        scope = PresenterScope()
        withPresenter(view)
    }

    private fun withPresenter(catsView: CatsView) {
        val appContext = this.applicationContext
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
        catsView.presenter = catsPresenter
        catsPresenter.attachView(catsView)
        catsPresenter.onInitComplete()
    }

    override fun onStop() {
        scope.cancel()
        if (isFinishing) {
            catsPresenter.detachView()
        }
        super.onStop()
    }
}