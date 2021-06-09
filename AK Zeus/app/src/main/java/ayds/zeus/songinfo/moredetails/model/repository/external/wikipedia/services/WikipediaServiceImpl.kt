package ayds.zeus.songinfo.moredetails.model.repository.external.wikipedia.services

import ayds.zeus.songinfo.moredetails.model.entities.WikipediaArticle
import ayds.zeus.songinfo.moredetails.model.repository.external.wikipedia.WikipediaService
import retrofit2.Response

internal class WikipediaServiceImpl(
    private val wikipediaToArticleResolver: WikipediaToArticleResolver,
    private val wikipediaAPI: WikipediaAPI
) : WikipediaService {
    override fun getArticle(artistName: String): WikipediaArticle? {
        val callResponse = getArticleFromService(artistName)
        return wikipediaToArticleResolver.getArticleFromExternalData(callResponse.body())
    }

    private fun getArticleFromService(query: String): Response<String> {
        return wikipediaAPI.getArtistInfo(query).execute()
    }
}