package ayds.zeus.songinfo.moredetails.model.broker.proxies

import android.util.Log
import ayds.zeus.songinfo.moredetails.model.entities.Card
import ayds.zeus.songinfo.moredetails.model.entities.EmptyCard
import ayds.zeus.songinfo.moredetails.model.entities.Source
import ayds.zeus3.wikipedia.WikipediaArticle
import ayds.zeus3.wikipedia.WikipediaService

internal interface WikipediaProxy {
    fun getCard(artistName: String): Card
}

internal class WikipediaProxyImpl(private val service: WikipediaService) : WikipediaProxy {
    override fun getCard(artistName: String): Card {
        var artistCard: Card = EmptyCard()
        try {
            val article = service.getArticle(artistName)
            article?.let { artistCard = map(it) }
        } catch (e: Exception) {
            Log.w("Card", "ERROR : $e")
        }
        return artistCard
    }

    private fun map(article: WikipediaArticle) = Card(
        article.info,
        article.url,
        article.logoUrl,
        Source.WIKIPEDIA
    )

}