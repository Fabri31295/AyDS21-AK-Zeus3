package ayds.zeus.songinfo.moredetails.view

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import ayds.observer.Observable
import ayds.observer.Subject
import ayds.zeus.songinfo.R
import ayds.zeus.songinfo.moredetails.model.MoreDetailsModel
import ayds.zeus.songinfo.moredetails.model.MoreDetailsModelModule
import ayds.zeus.songinfo.moredetails.model.repository.Source
import ayds.zeus.songinfo.moredetails.model.repository.entities.Card
import ayds.zeus.songinfo.moredetails.model.repository.entities.EmptyCard
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

    private lateinit var artistDescriptionPane: TextView
    private lateinit var descriptionSourcePane: TextView
    private lateinit var sourceImagePane: ImageView
    private lateinit var openUrlButton: Button

    override var uiState: MoreDetailsUiState = MoreDetailsUiState()
    override val uiEventObservable: Observable<MoreDetailsUiEvent> = onActionSubject

    override fun openSourcePage() {
        openExternalUrl(uiState.urlString)
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
                .subscribe { value -> updateWithNewCard(value) }
    }

    private fun updateWithNewCard(card: Card){
        updateUiState(card)
        showCardInfoActivity()
    }

    private fun updateUiState(card: Card) {
        when (card) {
            is EmptyCard -> updateNoResultUiState()
            else -> updateCardUiState(card)
        }
    }

    private fun updateCardUiState(card: Card) {
        uiState = uiState.copy(
            urlString = card.url,
            cardInfo = cardInfoHelper.getCardInfoText(card, uiState.artistName),
            actionsEnabled = true,
            urlLogoImage = card.logoUrl,
            source = card.source
        )
    }

    private fun updateNoResultUiState() {
        uiState = uiState.copy(
            urlString = "",
            cardInfo = cardInfoHelper.getCardInfoText(artistName = ""),
            actionsEnabled = false,
            urlLogoImage = "",
            source = Source.EMPTY
        )
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
        descriptionSourcePane = findViewById(R.id.textPaneSource)
        openUrlButton = findViewById(R.id.openUrlButton)
        sourceImagePane = findViewById(R.id.imageView)
    }

    private fun initListeners() {
        openUrlButton.setOnClickListener {
            openSourcePage()
        }
    }

    private fun notifyShowCardInfo() {
        onActionSubject.notify(MoreDetailsUiEvent.ShowCardInfo)
    }

    private fun showCardInfoActivity() {
        runOnUiThread {
            showSourceImage()
            showSourceLabel()
            showInfo()
        }
    }

    private fun showSourceImage() {
        Picasso.get().load(uiState.urlLogoImage).into(sourceImagePane)
    }

    private fun showSourceLabel() {
        descriptionSourcePane.text =
            when (uiState.source) {
                Source.WIKIPEDIA -> "From Wikipedia.org"
                else -> "Invalid source"
            }
    }

    private fun showInfo() {
        artistDescriptionPane.text = HtmlCompat.fromHtml(uiState.cardInfo, HtmlCompat.FROM_HTML_MODE_LEGACY)

    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }

}