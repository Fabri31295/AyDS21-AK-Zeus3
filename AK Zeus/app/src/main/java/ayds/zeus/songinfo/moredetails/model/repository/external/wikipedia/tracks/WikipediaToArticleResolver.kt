package ayds.zeus.songinfo.moredetails.model.repository.external.wikipedia.tracks

import ayds.zeus.songinfo.moredetails.model.entities.WikipediaArticle
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body

interface WikipediaToArticleResolver{
    fun getArticleFromExternalData(serviceData: String?): WikipediaArticle?
}

private const val JSON_SNIPPET = "snippet"
private const val JSON_PAGE_ID = "pageid"
private const val URL_WIKIPEDIA = "https://en.wikipedia.org/w/"
private const val WIKIPEDIA_SHORT_URL = "https://en.wikipedia.org/?curid="
private const val JSON_QUERY = "query"
private const val JSON_SEARCH = "search"
private const val JSON_TITLE = "title"

internal class JsonToArticleResolver : WikipediaToArticleResolver{

    private lateinit var artistName: String
    private lateinit var wikipediaAPI: WikipediaAPI

    override fun getArticleFromExternalData(serviceData: String?): WikipediaArticle? =
        try{
            serviceData?.getResponseJson()?.let { item ->
                WikipediaArticle(
                    item.getName(),
                    item.getInfo(),
                    item.getUrl()
                )
            }
        }catch(e: Exception){
            null
        }

    private fun String?.getResponseJson(): JsonObject {
        val gson = Gson()
        return gson.fromJson(this, JsonObject::class.java)
    }

    private fun JsonObject.getName(): String {
        return this[JSON_TITLE].asString
    }

    private fun JsonObject.getInfo(): String {
        TODO("Refactorizar")
        val snippet = getDataFromResponse(JSON_SNIPPET)
        return snippet.asString.replace("\\n", "\n")
    }

    private fun JsonObject.getUrl(): String {
        return WIKIPEDIA_SHORT_URL + this[JSON_PAGE_ID].asString
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
        return wikipediaAPI.getArtistInfo(artistName).execute() //repetido, debe estar mal
    }
}

