package ayds.zeus.songinfo.moredetails.model.broker

import ayds.zeus.songinfo.moredetails.model.broker.proxies.Proxy
import ayds.zeus.songinfo.moredetails.model.entities.Card

interface Broker {
    fun getCardList(artistName: String): List<Card>
}

class BrokerImpl(private val wikipediaProxy: Proxy, private val lastFMProxy: Proxy) : Broker {
    override fun getCardList(artistName: String): List<Card> {
        val wikipediaCard = wikipediaProxy.getCard(artistName)
        val lastFMCard = lastFMProxy.getCard(artistName)
        return listOf(wikipediaCard, lastFMCard)
    }

}