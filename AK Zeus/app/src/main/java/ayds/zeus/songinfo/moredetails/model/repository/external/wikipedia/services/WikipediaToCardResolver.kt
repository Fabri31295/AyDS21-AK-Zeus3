package ayds.zeus.songinfo.moredetails.model.repository.external.wikipedia.services

import ayds.zeus.songinfo.moredetails.model.entities.CardImpl
import com.google.gson.Gson
import com.google.gson.JsonObject

interface WikipediaToArticleResolver {
    fun getArticleFromExternalData(serviceData: String?): CardImpl?
}

private const val JSON_SNIPPET = "snippet"
private const val JSON_PAGE_ID = "pageid"
private const val WIKIPEDIA_SHORT_URL = "https://en.wikipedia.org/?curid="
private const val JSON_TITLE = "title"
private const val JSON_QUERY = "query"
private const val JSON_SEARCH = "search"
private const val LOGO_URL = "https://upload.wikimedia.org/wikipedia/commons/8/8c/Wikipedia-logo-v2-es.png"
private const val SOURCE = 1

internal class JsonToArticleResolver : WikipediaToArticleResolver {

    override fun getArticleFromExternalData(serviceData: String?): CardImpl? =
        try {
            serviceData?.getResponseJson()?.getArtistJson()?.let { item ->
                CardImpl(
                    item.getInfo(),
                    item.getUrl(),
                    LOGO_URL,
                    SOURCE
                )
            }
        } catch (e: Exception) {
            null
        }

    private fun String?.getResponseJson(): JsonObject {
        val gson = Gson()
        return gson.fromJson(this, JsonObject::class.java)
    }

    private fun JsonObject.getInfo(): String {
        return this[JSON_SNIPPET].asString.replace("\\n", "\n")
    }

    private fun JsonObject.getUrl(): String {
        return WIKIPEDIA_SHORT_URL + this[JSON_PAGE_ID].asString
    }

    private fun JsonObject.getArtistJson(): JsonObject {
        val query = this[JSON_QUERY].asJsonObject
        return query[JSON_SEARCH].asJsonArray[0].asJsonObject
    }
}

