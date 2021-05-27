package ayds.zeus.songinfo.moredetails.model.repository.external.wikipedia.tracks

import ayds.zeus.songinfo.moredetails.model.entities.WikipediaArticle
import com.google.gson.Gson
import com.google.gson.JsonObject

interface WikipediaToArticleResolver{
    fun getArticleFromExternalData(serviceData: String?): WikipediaArticle?
}

private const val JSON_SNIPPET = "snippet"
private const val JSON_PAGE_ID = "pageid"
private const val IMAGE_WIKIPEDIA =
        "https://upload.wikimedia.org/wikipedia/commons/8/8c/Wikipedia-logo-v2-es.png"
private const val URL_WIKIPEDIA = "https://en.wikipedia.org/w/"
private const val WIKIPEDIA_SHORT_URL = "https://en.wikipedia.org/?curid="
private const val JSON_QUERY = "query"
private const val JSON_SEARCH = "search"

internal class JsonToArticleResolver : WikipediaToArticleResolver{
    override fun getArticleFromExternalData(serviceData: String?): WikipediaArticle? =
        try{
            serviceData?.getResponseJson().let {
                TODO("FALTA CREAR EL ARTICLE")
            }
        }catch(e: Exception){
            null
        }

    private fun String?.getResponseJson(): JsonObject {
        val gson = Gson()
        return gson.fromJson(this, JsonObject::class.java)
    }

}