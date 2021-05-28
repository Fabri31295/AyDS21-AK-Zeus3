package ayds.zeus.songinfo.moredetails.model.repository

import ayds.zeus.songinfo.moredetails.model.entities.WikipediaArticle
import ayds.zeus.songinfo.moredetails.model.repository.external.wikipedia.WikipediaService
import ayds.zeus.songinfo.moredetails.model.repository.local.wikipedia.WikipediaLocalStorage


interface ArticleRepository {
    fun getArticle(artistName: String): WikipediaArticle
}

internal class ArticleRepositoryImpl(
        private val wikipediaLocalStorage: WikipediaLocalStorage,
        private val wikipediaService: WikipediaService
): ArticleRepository{
    override fun getArticle(artistName: String): WikipediaArticle {
        //aca es un wikipediaArticle
        val articleInfo = wikipediaLocalStorage.getArticle(artistName)
        if (articleInfo != null)
            markArticleAsLocal(articleInfo)
        else
            wikipediaService.getArticle(artistName)
        return articleInfo
    }

    private fun markArticleAsLocal(articleInfo: WikipediaArticle){
        articleInfo.isLocallyStoraged = true
    }
}