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
import ayds.zeus.songinfo.moredetails.model.repository.WikipediaLocalStorage
import ayds.zeus.songinfo.moredetails.model.repository.WikipediaLocalStorageImpl
import ayds.zeus.songinfo.moredetails.model.repository.external.wikipedia.tracks.WikipediaAPI
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
    private lateinit var dataBase: WikipediaLocalStorage
    private lateinit var artistName: String
    private lateinit var urlString: String
    private lateinit var openUrlButton: Button
    private lateinit var retrofit: Retrofit
    private lateinit var wikipediaAPI: WikipediaAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)

        initStorage()
        showArtistInfoAsync()
    }

    private fun initStorage() {
        dataBase = WikipediaLocalStorageImpl(this)
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

}