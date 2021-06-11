package ayds.zeus.songinfo.moredetails.model.repository.external.wikipedia

import ayds.zeus.songinfo.moredetails.model.entities.CardImpl

interface WikipediaService {
    fun getCard(artistName: String): CardImpl?
}