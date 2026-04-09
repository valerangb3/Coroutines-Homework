package otus.homework.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.coroutines.coroutineScope
import java.net.SocketTimeoutException

class MainActivity : AppCompatActivity() {

    lateinit var catsPresenter: CatsPresenter

    lateinit var scope: PresenterScope

    private val diContainer = DiContainer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        val appContext = this.applicationContext
        setContentView(view)
        scope = PresenterScope()
        catsPresenter = CatsPresenter(
            catsService = diContainer.service,
            coroutineScope = scope,
            onErrorRequest = { exception ->
                if (exception is SocketTimeoutException) {
                    Toast.makeText(appContext, "Не удалось получить ответ от сервера", Toast.LENGTH_SHORT).show()
                } else {
                    CrashMonitor.trackWarning()
                    Toast.makeText(appContext, exception.message, Toast.LENGTH_SHORT).show()
                }
            }
        )
        view.presenter = catsPresenter
        catsPresenter.attachView(view)
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