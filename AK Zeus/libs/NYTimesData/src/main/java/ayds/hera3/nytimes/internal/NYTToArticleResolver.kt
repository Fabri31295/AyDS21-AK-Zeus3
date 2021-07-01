package ayds.hera3.nytimes.internal

import ayds.hera3.nytimes.entities.*
import com.google.gson.Gson
import com.google.gson.JsonObject
import java.lang.Exception

private const val DOCS = "docs"
private const val WEB_URL = "web_url"
private const val ABSTRACT = "abstract"
private const val RESPONSE = "response"

internal interface NYTToArticleResolver {
    fun getFromExternalData(data: String?): Article
}

internal class JsonToArticleResolver : NYTToArticleResolver {
    override fun getFromExternalData(data: String?): Article =
        try {
            data.getFirstItem().let { item ->
                NYTimesArticle(item.getArtistInfo(), item.getUrl())
            }
        } catch (e: Exception) {
            EmptyArticle
        }

    private fun String?.getFirstItem(): JsonObject {
        val jobj = Gson().fromJson(this, JsonObject::class.java)

        val response = jobj[RESPONSE].asJsonObject
        val items = response[DOCS].asJsonArray

        return items[0].asJsonObject
    }

    private fun JsonObject.getArtistInfo() = this[ABSTRACT].asString

    private fun JsonObject.getUrl() = this[WEB_URL].asString
}