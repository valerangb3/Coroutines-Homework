package otus.homework.coroutines

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import otus.homework.coroutines.presentation.CatFact

class CatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ICatsView {
    var refreshHandler: (() -> Unit)? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        findViewById<Button>(R.id.button).setOnClickListener {
            //presenter?.onInitComplete()
            refreshHandler?.invoke()
        }
    }

    override fun populate(fact: CatFact, setImage: ((view: ImageView, srcImage: String) -> Unit)?) {
        findViewById<TextView>(R.id.fact_textView).text = fact.fact
        fact.image?.let { srcImg ->
            val imageView = findViewById<ImageView>(R.id.cat_imageView)
            setImage?.invoke(imageView, srcImg)
        }
    }
}

interface ICatsView {
    fun populate(fact: CatFact, setImage: ((view: ImageView, srcImage: String) -> Unit)? = null)
}