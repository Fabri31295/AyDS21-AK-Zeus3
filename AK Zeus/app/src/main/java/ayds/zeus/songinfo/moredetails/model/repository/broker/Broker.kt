package ayds.zeus.songinfo.moredetails.model.repository.broker

import ayds.zeus.songinfo.moredetails.model.repository.broker.proxies.Proxy
import ayds.zeus.songinfo.moredetails.model.entities.Card

interface Broker {
    fun getCardList(artistName: String): List<Card>
}

class BrokerImpl(private val proxyList: List<Proxy>) : Broker {

    override fun getCardList(artistName: String): List<Card> {
        val cardList: MutableList<Card> = mutableListOf()
        for (proxy in proxyList) {
            cardList.add(proxy.getCard(artistName))
        }
        return cardList
    }

}