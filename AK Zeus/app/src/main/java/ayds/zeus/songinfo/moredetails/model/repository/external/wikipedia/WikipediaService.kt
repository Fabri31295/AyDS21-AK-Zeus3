package ayds.zeus.songinfo.moredetails.model.repository.external.wikipedia

import ayds.zeus.songinfo.moredetails.model.entities.WikipediaArticle

interface WikipediaService {
    fun getArticle(artistName: String): WikipediaArticle?
}