package ayds.zeus.songinfo.moredetails.model.repository

import android.util.Log
import ayds.zeus.songinfo.moredetails.model.entities.Card
import ayds.zeus.songinfo.moredetails.model.repository.external.wikipedia.WikipediaService
import ayds.zeus.songinfo.moredetails.model.repository.local.wikipedia.WikipediaLocalStorage
import ayds.zeus.songinfo.moredetails.model.entities.EmptyCard

interface ArticleRepository {
    fun getArticle(artistName: String): Card
}

internal class ArticleRepositoryImpl(
    private val wikipediaLocalStorage: WikipediaLocalStorage,
    private val wikipediaService: WikipediaService
) : ArticleRepository {

    override fun getArticle(artistName: String): Card {
        var articleInfo = wikipediaLocalStorage.getArticle(artistName)

        when {
            articleInfo != null -> markArticleAsLocal(articleInfo)
            else -> {
                try {
                    articleInfo = wikipediaService.getArticle(artistName)
                    if (articleInfo != null)
                        wikipediaLocalStorage.saveArticle(articleInfo, artistName)
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