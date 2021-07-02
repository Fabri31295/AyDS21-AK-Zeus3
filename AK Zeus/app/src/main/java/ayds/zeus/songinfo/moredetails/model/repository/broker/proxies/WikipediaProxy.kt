package ayds.zeus.songinfo.moredetails.model.repository.broker.proxies

import android.util.Log
import ayds.zeus.songinfo.moredetails.model.entities.Card
import ayds.zeus.songinfo.moredetails.model.entities.EmptyCard
import ayds.zeus.songinfo.moredetails.model.entities.Source
import ayds.zeus3.wikipedia.WikipediaArticle
import ayds.zeus3.wikipedia.WikipediaService

internal class WikipediaProxy(private val service: WikipediaService) : Proxy {

    override fun getCard(artistName: String): Card {
        var card: Card = EmptyCard()
        try {
            card = service.getArticle(artistName).toCard()
        } catch (e: Exception) {
            Log.w("Card", "ERROR : $e")
        }
        return card
    }

    private fun WikipediaArticle?.toCard() =
        when {
            this == null -> EmptyCard()
            else -> Card(info, url, logoUrl, Source.WIKIPEDIA)
        }
}