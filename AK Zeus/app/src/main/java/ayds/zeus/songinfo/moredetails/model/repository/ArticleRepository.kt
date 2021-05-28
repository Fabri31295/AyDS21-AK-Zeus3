package ayds.zeus.songinfo.moredetails.model.repository

import ayds.zeus.songinfo.moredetails.model.entities.Article
import ayds.zeus.songinfo.moredetails.model.repository.external.wikipedia.WikipediaService
import ayds.zeus.songinfo.moredetails.model.repository.local.wikipedia.WikipediaLocalStorage
import ayds.zeus.songinfo.moredetails.model.entities.EmptyArticle


interface ArticleRepository {
    fun getArticle(artistName: String): Article
}

internal class ArticleRepositoryImpl(
        private val wikipediaLocalStorage: WikipediaLocalStorage,
        private val wikipediaService: WikipediaService
): ArticleRepository{
    override fun getArticle(artistName: String): Article {
        val articleInfo = wikipediaLocalStorage.getArticle(artistName)

        if (articleInfo != null)
            markArticleAsLocal(articleInfo)
        else
            wikipediaService.getArticle(artistName)

        return articleInfo ?: EmptyArticle
    }

    private fun markArticleAsLocal(articleInfo: Article){
        articleInfo.isLocallyStoraged = true
    }
}