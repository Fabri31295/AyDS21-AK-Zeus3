package ayds.zeus.songinfo.moredetails.view

import android.content.Intent
import android.net.Uri
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
import ayds.zeus.songinfo.moredetails.model.repository.external.wikipedia.tracks.WikipediaAPI
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val IMAGE_WIKIPEDIA =
        "https://upload.wikimedia.org/wikipedia/commons/8/8c/Wikipedia-logo-v2-es.png"
private const val URL_WIKIPEDIA = "https://en.wikipedia.org/w/"

interface MoreDetailsView {
    var uiState: MoreDetailsUiState
    val uiEventObservable: Observable<MoreDetailsUiEvent>

    fun updateUrl(url: String)
    fun openWikipediaPage()
    fun getArtistInfoText(text: String, term: String): String
    fun showArtistInfoActivity(artistInfo: String)
}

class OtherInfoActivity : AppCompatActivity(), MoreDetailsView {

    private val onActionSubject = Subject<MoreDetailsUiEvent>()
    private lateinit var artistDescriptionPane: TextView
    private lateinit var wikipediaImagePane: ImageView
    private lateinit var wikipediaImage: RequestCreator
    private lateinit var openUrlButton: Button
    private lateinit var retrofit: Retrofit
    private lateinit var wikipediaAPI: WikipediaAPI
    private val articleInfo: ArticleDescriptionHelperImpl= ArticleDescriptionHelperImpl()
    private lateinit var moreDetailsModel: MoreDetailsModel
    override var uiState: MoreDetailsUiState= MoreDetailsUiState()
    override val uiEventObservable: Observable<MoreDetailsUiEvent> = onActionSubject

    override fun updateUrl(url: String) {
        uiState = uiState.copy(urlString = url)
    }

    override fun openWikipediaPage() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(uiState.urlString)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)

        initModule()
        initProperties()
        initRetrofit()
        initWikipediaAPI()
        initWikipediaImage()
        initViews()
        initListeners()
    }

    override fun getArtistInfoText(text: String, term: String): String {
        return articleInfo.getTextToHtml(text,term)
    }

    private fun initModule() {
        MoreDetailsViewModule.init(this)
        moreDetailsModel = MoreDetailsModelModule.getMoreDetailsModel()
    }

    private fun initProperties() {
        uiState = uiState.copy(artistName = intent.getStringExtra(ARTIST_NAME_EXTRA).toString())
    }

    private fun initRetrofit() {
        retrofit = Retrofit.Builder()
                .baseUrl(URL_WIKIPEDIA)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
    }

    private fun initWikipediaAPI() {
        wikipediaAPI = retrofit.create(WikipediaAPI::class.java)
    }

    private fun initWikipediaImage() {
        wikipediaImage = Picasso.get().load(IMAGE_WIKIPEDIA)
    }

    private fun initViews() {
        artistDescriptionPane = findViewById(R.id.textPane2)
        openUrlButton = findViewById(R.id.openUrlButton)
        wikipediaImagePane = findViewById(R.id.imageView)
    }

    private fun initListeners() {
        openUrlButton.setOnClickListener {
            openWikipediaPage()
            showArtistInfoAction()
        }
    }

    private fun notifyShowArtistInfo(){
        onActionSubject.notify(MoreDetailsUiEvent.ShowArtistInfo)
    }

    private fun showArtistInfoAction() {
        notifyShowArtistInfo()
    }

    override fun showArtistInfoActivity(artistInfo: String) {
        runOnUiThread {
            showImageWikipedia()
            showInfoArtist(artistInfo)
        }
    }

    private fun showImageWikipedia() {
        wikipediaImage.into(wikipediaImagePane)
    }

    private fun showInfoArtist(text: String) {
        artistDescriptionPane.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }

}