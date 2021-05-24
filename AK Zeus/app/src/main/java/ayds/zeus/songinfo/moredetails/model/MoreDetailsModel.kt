package ayds.zeus.songinfo.moredetails.model

import ayds.zeus.songinfo.moredetails.model.entities.WikipediaArticle
import ayds.zeus.songinfo.moredetails.model.repository.ArtistRepository

interface MoreDetailsModel{
    fun getArtistByName(artistName: String): WikipediaArticle
}

internal class MoreDetailsModelImpl(private val repository: ArtistRepository) : MoreDetailsModel{

    override fun getArtistByName(artistName: String): WikipediaArticle {
       return repository.getArtist(artistName)
    }
}