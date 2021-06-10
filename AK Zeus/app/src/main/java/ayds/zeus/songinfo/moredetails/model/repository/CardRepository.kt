package ayds.zeus.songinfo.moredetails.model.repository

import android.util.Log
import ayds.zeus.songinfo.moredetails.model.entities.Card
import ayds.zeus.songinfo.moredetails.model.repository.external.wikipedia.WikipediaService
import ayds.zeus.songinfo.moredetails.model.repository.local.wikipedia.CardLocalStorage
import ayds.zeus.songinfo.moredetails.model.entities.EmptyCard

interface CardRepository {
    fun getArticle(artistName: String): Card
}

internal class CardRepositoryImpl(
    private val cardLocalStorage: CardLocalStorage,
    private val wikipediaService: WikipediaService
) : CardRepository {

    override fun getArticle(artistName: String): Card {
        var articleInfo = cardLocalStorage.getArticle(artistName)

        when {
            articleInfo != null -> markArticleAsLocal(articleInfo)
            else -> {
                try {
                    articleInfo = wikipediaService.getArticle(artistName)
                    if (articleInfo != null)
                        cardLocalStorage.saveArticle(articleInfo, artistName)
                } catch (e: Exception) {
                    Log.w("Wikipedia article", "ERROR : $e")
                }
            }
        }
        return articleInfo ?: EmptyCard
    }

    private fun markArticleAsLocal(cardInfo: Card) {
        cardInfo.isLocallyStoraged = true
    }
}