package ayds.zeus.songinfo.moredetails.model

import ayds.zeus.songinfo.moredetails.model.entities.Article
import ayds.zeus.songinfo.moredetails.model.repository.ArticleRepository

interface MoreDetailsModel {
    fun getArtistByName(artistName: String): Article
}

internal class MoreDetailsModelImpl(private val repository: ArticleRepository) : MoreDetailsModel {

    override fun getArtistByName(artistName: String): Article {
        return repository.getArticle(artistName)
    }
}