package ayds.zeus.songinfo.moredetails.model.repository.external.wikipedia.services

import ayds.zeus.songinfo.moredetails.model.entities.WikipediaCard
import com.google.gson.Gson
import com.google.gson.JsonObject

interface WikipediaToArticleResolver {
    fun getArticleFromExternalData(serviceData: String?): WikipediaCard?
}

private const val JSON_SNIPPET = "snippet"
private const val JSON_PAGE_ID = "pageid"
private const val WIKIPEDIA_SHORT_URL = "https://en.wikipedia.org/?curid="
private const val JSON_TITLE = "title"
private const val JSON_QUERY = "query"
private const val JSON_SEARCH = "search"

internal class JsonToArticleResolver : WikipediaToArticleResolver {

    override fun getArticleFromExternalData(serviceData: String?): WikipediaCard? =
        try {
            serviceData?.getResponseJson()?.getArtistJson()?.let { item ->
                WikipediaCard(
                    item.getInfo(),
                    item.getUrl()
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

