package ayds.zeus.songinfo.moredetails.model.repository

import ayds.zeus.songinfo.moredetails.model.entities.WikipediaArticle


interface ArticleRepository {
    fun getArticle(artistName: String): WikipediaArticle
}

internal class ArticleRepositoryImpl(
        private val artistInfoStorage: ArtistInfoStorage
): ArticleRepository{
    override fun getArticle(artistName: String): WikipediaArticle {
        val info = artistInfoStorage.getInfo(artistName)
        return WikipediaArticle(artistName, info)
    }
}