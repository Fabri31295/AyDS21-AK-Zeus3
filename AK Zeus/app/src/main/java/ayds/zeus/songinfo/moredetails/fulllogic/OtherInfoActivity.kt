package ayds.zeus.songinfo.moredetails.fulllogic

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import ayds.zeus.songinfo.R
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*

class OtherInfoActivity : AppCompatActivity() {

    private lateinit var artistDescriptionPane: TextView
    private lateinit var dataBase: ArtistInfoStorage
    private lateinit var artistName: String
    private lateinit var openUrlButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)
        initProperties()
        initListeners()
        showArtistInfo()
    }

    private fun initProperties() {
        artistDescriptionPane = findViewById(R.id.textPane2)
        artistName = intent.getStringExtra("artistName").toString()
        openUrlButton = findViewById(R.id.openUrlButton)
        dataBase = ArtistInfoStorage(this)
    }

    private fun initListeners() {
        openUrlButton.setOnClickListener {
            openWikipediaPage()
        }
    }

    private fun showArtistInfo() {
        Thread {
            val infoArtist = getArtistInfo()
            runOnUiThread {
                showImageWikipedia()
                showInfoArtist(infoArtist)
            }
        }.start()
    }

    private fun getArtistInfo(): String {
        val infoArtistText = getArtistInfoDataBase()
        return if (infoArtistText != null)
            "[*]$infoArtistText"
        else
            getDescriptionArtistInfo()
    }

    private fun getArtistInfoDataBase(): String? {
        return dataBase.getInfo(artistName)
    }

    private fun getDescriptionArtistInfo(): String {
        val descriptionArtistHTML = getDescriptionArtistToHTML()
        saveToDatabase(descriptionArtistHTML)
        return descriptionArtistHTML
    }

    private fun getDescriptionArtistToHTML(): String {
        val snippet = getDataFromResponse(JSON_SNIPPET)
        val descriptionArtist = snippet.asString.replace("\\n", "\n")
        return textToHtml(descriptionArtist, artistName)
    }

    private fun saveToDatabase(text: String) {
        dataBase.saveArtist(artistName, text)
    }

    private fun openWikipediaPage() {
        val urlString = buildWikipediaURL()
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(urlString)
        startActivity(intent)
    }

    private fun buildWikipediaURL(): String {
        val pageid = getDataFromResponse(JSON_PAGE_ID)
        return "https://en.wikipedia.org/?curid=$pageid"
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
        val query = jobj["query"].asJsonObject
        return query["search"].asJsonArray[0].asJsonObject[name]
    }

    private fun getCallResponse(): Response<String> {
        val retrofit = Retrofit.Builder()
                .baseUrl(URL_WIKIPEDIA)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
        val wikipediaAPI = retrofit.create(WikipediaAPI::class.java)
        return wikipediaAPI.getArtistInfo(artistName).execute()
    }

    private fun showInfoArtist(text: String) {
        artistDescriptionPane.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    private fun showImageWikipedia() {
        Picasso.get().load(IMAGE_WIKIPEDIA).into(findViewById<View>(R.id.imageView) as ImageView)
    }

    private fun textToHtml(text: String, term: String?): String {
        val builder = StringBuilder()
        builder.append("<html><div width=400>")
        builder.append("<font face=\"arial\">")
        val textWithBold = text
                .replace("'", " ")
                .replace("\n", "<br>")
                .replace("(?i)" + term!!.toRegex(), "<b>" + term.toUpperCase(Locale.ROOT) + "</b>")
        builder.append(textWithBold)
        builder.append("</font></div></html>")
        return builder.toString()
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
        const val JSON_SNIPPET = "snippet"
        const val JSON_PAGE_ID = "pageid"
        const val IMAGE_WIKIPEDIA = "https://upload.wikimedia.org/wikipedia/commons/8/8c/Wikipedia-logo-v2-es.png"
        const val URL_WIKIPEDIA = "https://en.wikipedia.org/w/"
    }
}
