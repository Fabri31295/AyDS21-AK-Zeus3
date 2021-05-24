package ayds.zeus.songinfo.moredetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import ayds.zeus.songinfo.R
import ayds.zeus.songinfo.moredetails.model.repository.ArtistInfoStorage
import ayds.zeus.songinfo.moredetails.model.repository.ArtistInfoStorageImpl
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*

private const val JSON_SNIPPET = "snippet"
private const val JSON_PAGE_ID = "pageid"
private const val IMAGE_WIKIPEDIA =
    "https://upload.wikimedia.org/wikipedia/commons/8/8c/Wikipedia-logo-v2-es.png"
private const val URL_WIKIPEDIA = "https://en.wikipedia.org/w/"
private const val WIKIPEDIA_SHORT_URL = "https://en.wikipedia.org/?curid="
private const val JSON_QUERY = "query"
private const val JSON_SEARCH = "search"
private const val STORED_PREFIX = "[*]"

class OtherInfoActivity : AppCompatActivity() {

    private lateinit var artistDescriptionPane: TextView
    private lateinit var wikipediaImagePane: ImageView
    private lateinit var wikipediaImage: RequestCreator
    private lateinit var dataBase: ArtistInfoStorage
    private lateinit var artistName: String
    private lateinit var urlString: String
    private lateinit var openUrlButton: Button
    private lateinit var retrofit: Retrofit
    private lateinit var wikipediaAPI: WikipediaAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)

        initProperties()
        initRetrofit()
        initWikipediaAPI()
        initWikipediaImage()
        initStorage()
        initViews()
        initListeners()
        showArtistInfoAsync()
    }

    private fun initProperties() {
        artistName= intent.getStringExtra(ARTIST_NAME_EXTRA).toString()
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

    private fun initStorage() {
        dataBase = ArtistInfoStorageImpl(this)
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

    private fun showArtistInfoAsync() {
        Thread {
            showArtistInfo()
        }.start()
    }

    private fun showArtistInfo() {
        urlString = getWikipediaURL()
        showArtistInfoActivity(getArtistInfo())
    }

    private fun showArtistInfoActivity(artistInfo: String) {
        runOnUiThread {
            showImageWikipedia()
            showInfoArtist(artistInfo)
        }
    }

    private fun getArtistInfo(): String {
        var infoArtistText = getArtistInfoDataBase()
        if (infoArtistText != null)
            infoArtistText = STORED_PREFIX + "$infoArtistText"
        else {
            infoArtistText = getDescriptionArtistToHTML()
            dataBase.saveArtist(artistName, infoArtistText)
        }
        return infoArtistText
    }

    private fun getArtistInfoDataBase(): String? {
        return dataBase.getInfo(artistName)
    }

    private fun getDescriptionArtistToHTML(): String {
        val snippet = getDataFromResponse(JSON_SNIPPET)
        val descriptionArtist = snippet.asString.replace("\\n", "\n")
        return textToHtml(descriptionArtist, artistName)
    }

    private fun openWikipediaPage() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(urlString)
        startActivity(intent)
    }

    private fun getWikipediaURL(): String {
        val pageid = getDataFromResponse(JSON_PAGE_ID)
        return WIKIPEDIA_SHORT_URL + "$pageid"
    }

    private fun getDataFromResponse(name: String): JsonElement {
        val jobj = getResponseJson()
        return getDataFromJson(jobj, name)
    }

    private fun getResponseJson(): JsonObject {
        val callResponse = getCallResponse()
        val gson = Gson()
        return gson.fromJson(callResponse.body(), JsonObject::class.java)
    }

    private fun getDataFromJson(jobj: JsonObject, name: String): JsonElement {
        val query = jobj[JSON_QUERY].asJsonObject
        return query[JSON_SEARCH].asJsonArray[0].asJsonObject[name]
    }

    private fun getCallResponse(): Response<String> {
        return wikipediaAPI.getArtistInfo(artistName).execute()
    }

    private fun showInfoArtist(text: String) {
        artistDescriptionPane.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    private fun showImageWikipedia() {
        wikipediaImage.into(wikipediaImagePane)
    }

    private fun textToHtml(text: String, term: String): String {
        val builder = StringBuilder()
        builder.append("<html><div width=400>")
        builder.append("<font face=\"arial\">")
        val textWithBold = text
            .replace("'", " ")
            .replace("\n", "<br>")
            .replace("(?i)" + term.toRegex(), "<b>" + term.toUpperCase(Locale.ROOT) + "</b>")
        builder.append(textWithBold)
        builder.append("</font></div></html>")
        return builder.toString()
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }
}
