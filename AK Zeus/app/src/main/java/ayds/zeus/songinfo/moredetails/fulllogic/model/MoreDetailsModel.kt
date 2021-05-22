package ayds.zeus.songinfo.moredetails.fulllogic.model

import ayds.zeus.songinfo.moredetails.fulllogic.WikipediaAPI
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.Response
import java.util.*

private const val STORED_PREFIX = "[*]"
private const val JSON_SNIPPET = "snippet"
private const val JSON_QUERY = "query"
private const val JSON_SEARCH = "search"

interface MoreDetails{
    fun getArtistInfo(artistName: String):String
}

internal class MoreDetailsModelImpl(private val repository: ArtistInfoStorage) : MoreDetails{

    override fun getArtistInfo(artistName: String): String {
        var infoArtistText = getArtistInfoDataBase(artistName)
        if (infoArtistText != null)
            infoArtistText = STORED_PREFIX + "$infoArtistText"
        else {
            infoArtistText = getDescriptionArtistToHTML()
            repository.saveArtist(artistName, infoArtistText)
        }
        return infoArtistText
    }

    private fun getArtistInfoDataBase(artistName: String): String? {
        return repository.getInfo(artistName)
    }

    private fun getDescriptionArtistToHTML(artistName:String): String {
        val snippet = getDataFromResponse(JSON_SNIPPET)
        val descriptionArtist = snippet.asString.replace("\\n", "\n")
        return textToHtml(descriptionArtist, artistName)
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

    private fun getCallResponse(artistName: String, wikipediaAPI: WikipediaAPI): Response<String> {
        return wikipediaAPI.getArtistInfo(artistName).execute()
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
}