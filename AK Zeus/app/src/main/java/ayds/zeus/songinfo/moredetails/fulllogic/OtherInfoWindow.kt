package ayds.zeus.songinfo.moredetails.fulllogic

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
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

class OtherInfoWindow : AppCompatActivity() {

    private lateinit var textPane2: TextView
    private lateinit var dataBase: DataBase
    private lateinit var artistName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)
        textPane2 = findViewById(R.id.textPane2)
        artistName = intent.getStringExtra("artistName").toString()
        dataBase = DataBase(this)
        getArtistInfo()
    }

    private fun getArtistInfo() {
        Thread {
            var text = getArtistInfoDataBase()
            text = if (text != null)
                "[*]$text"
            else
                getDescriptionArtistInfo()
            openWikipediaPage()
            runOnUiThread {
                showInfo(text)
            }
        }.start()
    }

    private fun getArtistInfoDataBase(): String? {
        return dataBase.getInfo(artistName)
    }

    private fun getDescriptionArtistInfo(): String {
        val snippet = getJsonElement("snippet")
        var text = ""
        text = snippet.asString.replace("\\n", "\n")
        var newText = textToHtml(text, artistName)
        saveToDatabase(newText)
        return newText
    }

    private fun saveToDatabase(text: String){
        dataBase.saveArtist(artistName, text)
    }

    private fun openWikipediaPage() {
        val urlString = getURL()
        findViewById<View>(R.id.openUrlButton).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(urlString)
            startActivity(intent)
        }
    }

    private fun getURL(): String {
        val pageid = getJsonElement("pageid")
        val urlString = "https://en.wikipedia.org/?curid=$pageid"
        return urlString
    }

    private fun getJsonElement(name: String): JsonElement{
        val callResponse = getCallResponse()
        println("JSON " + callResponse.body())
        val gson = Gson()
        val jobj = gson.fromJson(callResponse.body(), JsonObject::class.java)
        val query = jobj["query"].asJsonObject
        return query["search"].asJsonArray[0].asJsonObject[name]
    }

    private fun getCallResponse() : Response<String>{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://en.wikipedia.org/w/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        val wikipediaAPI = retrofit.create(WikipediaAPI::class.java)
        return wikipediaAPI.getArtistInfo(artistName).execute()
    }

    private fun showInfo(text: String){
        val imageUrl = "https://upload.wikimedia.org/wikipedia/commons/8/8c/Wikipedia-logo-v2-es.png"
        Picasso.get().load(imageUrl).into(findViewById<View>(R.id.imageView) as ImageView)
        textPane2.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
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
    }
}
