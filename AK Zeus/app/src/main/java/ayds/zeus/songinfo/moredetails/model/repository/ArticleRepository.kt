package ayds.zeus.songinfo.moredetails.model.repository

import ayds.zeus.songinfo.moredetails.model.entities.WikipediaArticle


interface ArticleRepository {
    fun getArticle(artistName: String): WikipediaArticle
}

internal class ArticleRepositoryImpl(
        private val wikipediaLocalStorage: WikipediaLocalStorage
): ArticleRepository{
    override fun getArticle(artistName: String): WikipediaArticle {
        val info = wikipediaLocalStorage.getInfo(artistName)
        return WikipediaArticle(artistName, info)
    }
}