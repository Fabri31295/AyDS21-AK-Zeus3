package ayds.hera3.nytimes.internal

import ayds.hera3.nytimes.entities.Article
import ayds.hera3.nytimes.NYTimesArticleService

internal class NYTimesArticleServiceImpl(
    private val nytArticleAPI: NYTimesAPI,
    private val jsonToArticleResolver: NYTToArticleResolver
) : NYTimesArticleService {

    override fun getArticle(artistName: String): Article {
        val response = nytArticleAPI.getArtistInfo(artistName).execute()

        return jsonToArticleResolver.getFromExternalData(response.body())
    }
}