package ayds.zeus.songinfo.moredetails.fulllogic.model

import ayds.zeus.songinfo.moredetails.fulllogic.model.entities.Artist
import ayds.zeus.songinfo.moredetails.fulllogic.model.repository.ArtistRepository

interface MoreDetailsModel{
    fun getArtistInfo(artistName: String): Artist
}

internal class MoreDetailsModelImpl(private val repository: ArtistRepository) : MoreDetailsModel{

    override fun getArtistInfo(artistName: String): Artist {
       return repository.getArtist(artistName)
    }
}