package ayds.zeus.songinfo.moredetails.view

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import ayds.observer.Observable
import ayds.observer.Subject
import ayds.zeus.songinfo.R
import ayds.zeus.songinfo.moredetails.model.MoreDetailsModel
import ayds.zeus.songinfo.moredetails.model.MoreDetailsModelModule
import ayds.zeus.songinfo.moredetails.model.entities.Card
import ayds.zeus.songinfo.moredetails.model.entities.EmptyCard
import ayds.zeus.songinfo.moredetails.model.entities.Source
import ayds.zeus.songinfo.utils.navigation.openExternalUrl
import com.squareup.picasso.Picasso

interface MoreDetailsView {
    var uiState: MoreDetailsUiState
    val uiEventObservable: Observable<MoreDetailsUiEvent>

    fun openSourcePage()
}

class OtherInfoActivity : AppCompatActivity(), MoreDetailsView {

    private val onActionSubject = Subject<MoreDetailsUiEvent>()
    private val cardInfoHelper: CardDescriptionHelper = MoreDetailsViewModule.cardInfoHelper
    private lateinit var moreDetailsModel: MoreDetailsModel
    private lateinit var spinner: Spinner
    private lateinit var artistDescriptionPane: TextView
    private lateinit var sourceImagePane: ImageView
    private lateinit var openUrlButton: Button

    override var uiState: MoreDetailsUiState = MoreDetailsUiState()
    override val uiEventObservable: Observable<MoreDetailsUiEvent> = onActionSubject

    override fun openSourcePage() {
        openExternalUrl(uiState.getCurrentCard().url)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)

        initModule()
        initProperties()
        initViews()
        initListeners()
        initObservers()
        notifyShowCardInfo()
    }

    private fun initObservers() {
        moreDetailsModel.cardObservable()
            .subscribe { value ->
                run {
                    initSpinner(value)
                    updateUiState(value)
                }
            }
    }

    private fun initSpinner(list: List<Card>) {
        val spinnerList: MutableList<String> = mutableListOf()
        spinnerList.addAll(list.map { it.source.sourceName })
        if (spinnerList.isEmpty()) spinnerList.add(Source.EMPTY.sourceName)
        runOnUiThread {
            spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerList)
        }
    }

    private fun updateUiState(cards: List<Card>) {
        uiState = when {
            cards.isEmpty() -> uiState.copy(cardList = listOf(EmptyCard()))
            else -> uiState.copy(cardList = cards)
        }

    }

    private fun initModule() {
        MoreDetailsViewModule.init(this)
        moreDetailsModel = MoreDetailsModelModule.getMoreDetailsModel()
    }

    private fun initProperties() {
        uiState = uiState.copy(artistName = intent.getStringExtra(ARTIST_NAME_EXTRA).toString())
    }

    private fun initViews() {
        artistDescriptionPane = findViewById(R.id.textPane2)
        spinner = findViewById(R.id.spinner)
        openUrlButton = findViewById(R.id.openUrlButton)
        sourceImagePane = findViewById(R.id.imageView)
    }

    private fun initListeners() {
        openUrlButton.setOnClickListener {
            openSourcePage()
        }
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                uiState.currentCardPosition = position
                showCardInfoActivity()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                showCardInfoActivity()
            }
        }
    }

    private fun notifyShowCardInfo() {
        onActionSubject.notify(MoreDetailsUiEvent.ShowCardInfo)
    }

    private fun showCardInfoActivity() {
        runOnUiThread {
            showSourceImage()
            showInfo()
        }
    }

    private fun showSourceImage() {
        Picasso.get().load(uiState.getCurrentCard().logoUrl).into(sourceImagePane)
    }

    private fun showInfo() {
        artistDescriptionPane.text =
            HtmlCompat.fromHtml(
                cardInfoHelper.getCardInfoText(uiState.getCurrentCard(), uiState.artistName),
                HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }

}