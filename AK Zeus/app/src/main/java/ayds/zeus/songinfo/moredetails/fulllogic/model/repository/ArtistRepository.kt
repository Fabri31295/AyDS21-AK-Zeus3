package ayds.zeus.songinfo.moredetails.fulllogic.model.repository

import ayds.zeus.songinfo.moredetails.fulllogic.model.entities.Artist

interface ArtistRepository {
    fun getArtist(artistName: String): Artist
}

internal class ArtistRepositoryImpl(
        private val artistInfoStorage: ArtistInfoStorage
): ArtistRepository{
    override fun getArtist(artistName: String): Artist {
        TODO()
    }
}