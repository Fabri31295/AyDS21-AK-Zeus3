package ayds.zeus.songinfo.moredetails.model.broker.proxies

import ayds.zeus.songinfo.moredetails.model.entities.Card

interface Proxy {
    fun getCard(artistName: String): Card
}