package ayds.zeus.songinfo.moredetails.model.repository.external.wikipedia.services

import ayds.zeus.songinfo.moredetails.model.entities.CardImpl
import ayds.zeus.songinfo.moredetails.model.repository.external.wikipedia.WikipediaService
import retrofit2.Response

internal class WikipediaServiceImpl(
    private val wikipediaToCardResolver: WikipediaToCardResolver,
    private val wikipediaAPI: WikipediaAPI
) : WikipediaService {
    override fun getCard(artistName: String): CardImpl? {
        val callResponse = getResponseFromService(artistName)
        return wikipediaToCardResolver.getCardFromExternalData(callResponse.body())
    }

    private fun getResponseFromService(query: String): Response<String> {
        return wikipediaAPI.getArtistInfo(query).execute()
    }
}