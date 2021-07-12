package ayds.zeus.songinfo.moredetails.model.repository.broker.proxies

import android.util.Log
import ayds.hera3.nytimes.NYTimesArticleService
import ayds.hera3.nytimes.entities.Article
import ayds.zeus.songinfo.moredetails.model.entities.Card
import ayds.zeus.songinfo.moredetails.model.entities.EmptyCard
import ayds.zeus.songinfo.moredetails.model.entities.Source

internal class NYTimesProxy(private val service: NYTimesArticleService) : Proxy {

    override fun getCard(artistName: String): Card {
        var card: Card = EmptyCard()
        try {
            card = service.getArticle(artistName).toCard()
        } catch (e: Exception) {
            Log.w("Card", "ERROR : $e")
        }
        return card
    }

    private fun Article?.toCard() =
        when {
            this == null -> EmptyCard()
            else ->
                if (artistInfo == "")
                    Card(NO_DESCRIPTION_FOUND, url, logoUrl, Source.NYTIMES)
                else
                    Card(artistInfo, url, logoUrl, Source.NYTIMES)
        }

}