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
import ayds.zeus.songinfo.moredetails.model.entities.Card
import ayds.zeus.songinfo.moredetails.model.entities.EmptyCard
import ayds.zeus.songinfo.utils.navigation.openExternalUrl
import com.squareup.picasso.Picasso


interface MoreDetailsView {
    var uiState: MoreDetailsUiState
    val uiEventObservable: Observable<MoreDetailsUiEvent>

    fun openWikipediaPage()
}

class OtherInfoActivity : AppCompatActivity(), MoreDetailsView {

    private val onActionSubject = Subject<MoreDetailsUiEvent>()
    private val articleInfoHelper: ArticleDescriptionHelper = MoreDetailsViewModule.articleInfoHelper
    private lateinit var moreDetailsModel: MoreDetailsModel

    private lateinit var artistDescriptionPane: TextView
    private lateinit var wikipediaImagePane: ImageView
    private lateinit var openUrlButton: Button

    override var uiState: MoreDetailsUiState = MoreDetailsUiState()
    override val uiEventObservable: Observable<MoreDetailsUiEvent> = onActionSubject

    override fun openWikipediaPage() {
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
        notifyShowArticleInfo()
    }

    private fun initObservers() {
        moreDetailsModel.articleObservable()
                .subscribe { value -> updateArticleInfo(value) }
    }

    private fun updateArticleInfo(card: Card){
        updateUiState(card)
        showArticleInfoActivity()
    }

    private fun updateUiState(card: Card) {
        when (card) {
            EmptyCard -> updateNoResultUiState()
            else -> updateArticleUiState(card)
        }
    }

    private fun updateArticleUiState(card: Card) {
        uiState = uiState.copy(
            urlString = card.url,
            articleInfo = articleInfoHelper.getArticleInfoText(card, uiState.artistName),
            actionsEnabled = true,
            urlLogoImage = card.sourceLogo,
            source = card.source
        )
    }

    private fun updateNoResultUiState() {
        uiState = uiState.copy(
            urlString = "",
            articleInfo = articleInfoHelper.getArticleInfoText(artistName = ""),
            actionsEnabled = false,
            urlLogoImage = "",
            source = -1
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
        openUrlButton = findViewById(R.id.openUrlButton)
        wikipediaImagePane = findViewById(R.id.imageView)
    }

    private fun initListeners() {
        openUrlButton.setOnClickListener {
            openWikipediaPage()
        }
    }

    private fun notifyShowArticleInfo() {
        onActionSubject.notify(MoreDetailsUiEvent.ShowArticleInfo)
    }

    private fun showArticleInfoActivity() {
        runOnUiThread {
            showImageWikipedia()
            showInfoArticle(uiState.articleInfo)
        }
    }

    private fun showImageWikipedia() {
        Picasso.get().load(uiState.urlLogoImage).into(wikipediaImagePane)
    }

    private fun showInfoArticle(text: String) {
        artistDescriptionPane.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)

    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }

}