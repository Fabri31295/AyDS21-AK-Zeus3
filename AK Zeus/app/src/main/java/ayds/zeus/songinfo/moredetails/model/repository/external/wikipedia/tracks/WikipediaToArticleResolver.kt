package ayds.zeus.songinfo.moredetails.model.repository.external.wikipedia.tracks

import ayds.zeus.songinfo.moredetails.model.entities.WikipediaArticle
import com.google.gson.Gson
import com.google.gson.JsonObject

interface WikipediaToArticleResolver{
    fun getArticleFromExternalData(serviceData: String?): WikipediaArticle?
}

private const val JSON_SNIPPET = "snippet"
private const val JSON_PAGE_ID = "pageid"
private const val WIKIPEDIA_SHORT_URL = "https://en.wikipedia.org/?curid="
private const val JSON_TITLE = "title"

internal class JsonToArticleResolver : WikipediaToArticleResolver{


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
        return this[JSON_SNIPPET].asString.replace("\\n", "\n")
    }

    private fun JsonObject.getUrl(): String {
        return WIKIPEDIA_SHORT_URL + this[JSON_PAGE_ID].asString
    }
}

