package ayds.zeus.songinfo.moredetails.model.repository

import android.util.Log
import ayds.zeus.songinfo.moredetails.model.entities.Article
import ayds.zeus.songinfo.moredetails.model.repository.external.wikipedia.WikipediaService
import ayds.zeus.songinfo.moredetails.model.repository.local.wikipedia.WikipediaLocalStorage
import ayds.zeus.songinfo.moredetails.model.entities.EmptyArticle
import ayds.zeus.songinfo.moredetails.model.entities.WikipediaArticle


interface ArticleRepository {
    fun getArticle(artistName: String): Article
}

internal class ArticleRepositoryImpl(
        private val wikipediaLocalStorage: WikipediaLocalStorage,
        private val wikipediaService: WikipediaService
): ArticleRepository{
    override fun getArticle(artistName: String): Article {
        var articleInfo = wikipediaLocalStorage.getArticle(artistName)

        when {
            articleInfo != null -> markArticleAsLocal(articleInfo)
            else -> {
                    try{
                        articleInfo = wikipediaService.getArticle(artistName)

                        articleInfo?.let {
                            when {
                                !(it.isSavedArticle()) -> wikipediaLocalStorage.saveArticle(articleInfo)
                            }
                        }
                    }catch (e: Exception){
                        Log.w("Wikipedia article","ERROR : $e")
                    }
            }
        }

        return articleInfo ?: EmptyArticle
    }

    private fun WikipediaArticle.isSavedArticle() = wikipediaLocalStorage.getArticle(name) != null

    private fun markArticleAsLocal(articleInfo: Article){
        articleInfo.isLocallyStoraged = true
    }
}