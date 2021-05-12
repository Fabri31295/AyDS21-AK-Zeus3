package ayds.zeus.songinfo.moredetails.fulllogic

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ayds.zeus.songinfo.R
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException

class OtherInfoWindow : AppCompatActivity() {

    private lateinit var textPane2: TextView
    private lateinit var dataBase: DataBase
    private lateinit var artistName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)
        textPane2 = findViewById(R.id.textPane2)
        artistName = intent.getStringExtra("artistName").toString()
        open()
    }

    private fun open() {
        dataBase = DataBase(this)
        getArtistInfo()
    }

    private fun getArtistInfo() {
        Thread {
            var text = DataBase.getInfo(dataBase, artistName)
            if (text != null) {
                text = "[*]$text"
            } else {
                try {
                    val snippet = getJsonElement("snippet")
                    text = getDescriptionArtistInfo(snippet)
                    openWikipediaPage()
                } catch (e1: IOException) {
                    e1.printStackTrace()
                }
            }
            val imageUrl = "https://upload.wikimedia.org/wikipedia/commons/8/8c/Wikipedia-logo-v2-es.png"
            val finalText = text
            runOnUiThread {
                Picasso.get().load(imageUrl).into(findViewById<View>(R.id.imageView) as ImageView)
                textPane2.text = Html.fromHtml(finalText)
            }
        }.start()
    }

    private fun getDescriptionArtistInfo(snippet: JsonElement): String {
        var newText = "No Results"
        if (snippet != null) {
            newText = snippet.asString.replace("\\n", "\n")
            newText = textToHtml(newText, artistName)
            saveToDatabase(newText)
        }
        return newText
    }
    private fun openWikipediaPage() {
        findViewById<View>(R.id.openUrlButton).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(getURL())
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
        val element = query["search"].asJsonArray[0].asJsonObject[name]
        return element
    }

    private fun getCallResponse() : Response<String>{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://en.wikipedia.org/w/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        val wikipediaAPI = retrofit.create(WikipediaAPI::class.java)
        val callResponse = wikipediaAPI.getArtistInfo(artistName).execute()
        return callResponse
    }

    private fun saveToDatabase(text: String){
        DataBase.saveArtist(dataBase, artistName, text)
    }

    private fun textToHtml(text: String, term: String?): String {
        val builder = StringBuilder()
        builder.append("<html><div width=400>")
        builder.append("<font face=\"arial\">")
        val textWithBold = text
            .replace("'", " ")
            .replace("\n", "<br>")
            .replace("(?i)" + term!!.toRegex(), "<b>" + term.toUpperCase() + "</b>")
        builder.append(textWithBold)
        builder.append("</font></div></html>")
        return builder.toString()
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName
    }
}
