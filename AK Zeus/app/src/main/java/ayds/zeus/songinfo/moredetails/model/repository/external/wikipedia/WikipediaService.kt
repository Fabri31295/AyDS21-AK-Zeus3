package ayds.zeus.songinfo.moredetails.model.repository.external.wikipedia

import ayds.zeus.songinfo.moredetails.model.entities.WikipediaCard

interface WikipediaService {
    fun getArticle(artistName: String): WikipediaCard?
}