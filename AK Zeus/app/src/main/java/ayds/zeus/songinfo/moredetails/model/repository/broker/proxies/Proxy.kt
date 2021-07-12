package ayds.zeus.songinfo.moredetails.model.repository.broker.proxies

import ayds.zeus.songinfo.moredetails.model.entities.Card

const val NO_DESCRIPTION_FOUND = "No description found..."

interface Proxy {
    fun getCard(artistName: String): Card
}