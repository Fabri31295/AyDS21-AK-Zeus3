package ayds.zeus.songinfo.moredetails.model

import ayds.zeus.songinfo.moredetails.model.entities.WikipediaArticle
import ayds.zeus.songinfo.moredetails.model.repository.ArticleRepository

interface MoreDetailsModel{
    fun getArtistByName(artistName: String): WikipediaArticle
}

internal class MoreDetailsModelImpl(private val repository: ArticleRepository) : MoreDetailsModel{

    override fun getArtistByName(artistName: String): WikipediaArticle {
       return repository.getArticle(artistName)
    }
}