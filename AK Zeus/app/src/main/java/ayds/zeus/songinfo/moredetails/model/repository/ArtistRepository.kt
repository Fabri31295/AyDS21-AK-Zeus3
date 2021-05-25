package ayds.zeus.songinfo.moredetails.model.repository

import ayds.zeus.songinfo.moredetails.model.entities.WikipediaArticle


interface ArtistRepository {
    fun getArticle(artistName: String): WikipediaArticle
}

internal class ArtistRepositoryImpl(
        private val artistInfoStorage: ArtistInfoStorage
): ArtistRepository{
    override fun getArticle(artistName: String): WikipediaArticle {
        val info = artistInfoStorage.getInfo(artistName)
        return WikipediaArticle(artistName, info)
    }
}