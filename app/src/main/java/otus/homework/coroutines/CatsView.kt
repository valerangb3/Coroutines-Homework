package otus.homework.coroutines

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import otus.homework.coroutines.presentation.CatFact
import otus.homework.coroutines.presentation.CatsPresenter

class CatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ICatsView {

    var presenter : CatsPresenter? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        findViewById<Button>(R.id.button).setOnClickListener {
            presenter?.onInitComplete()
        }
    }

    override fun populate(fact: CatFact) {
        findViewById<TextView>(R.id.fact_textView).text = fact.fact
        fact.image?.let {
            findViewById<ImageView>(R.id.cat_imageView).setImageBitmap(it)
        }
    }
}

interface ICatsView {
    fun populate(fact: CatFact)
}