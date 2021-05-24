package ayds.zeus.songinfo.moredetails.model.repository

import ayds.zeus.songinfo.moredetails.model.entities.WikipediaArticle


interface ArtistRepository {
    fun getArtist(artistName: String): WikipediaArticle
}

internal class ArtistRepositoryImpl(
        private val artistInfoStorage: ArtistInfoStorage
): ArtistRepository{
    override fun getArtist(artistName: String): WikipediaArticle {
        TODO()
    }
}