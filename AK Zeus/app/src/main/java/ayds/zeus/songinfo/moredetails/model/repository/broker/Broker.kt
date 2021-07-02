package ayds.zeus.songinfo.moredetails.model.repository.broker

import ayds.zeus.songinfo.moredetails.model.repository.broker.proxies.Proxy
import ayds.zeus.songinfo.moredetails.model.entities.Card
import ayds.zeus.songinfo.moredetails.model.entities.EmptyCard

interface Broker {
    fun getCardList(artistName: String): List<Card>
}

class BrokerImpl(private val sourceToProxyMap: List<Proxy>) : Broker {

    override fun getCardList(artistName: String): List<Card> {
        val cardList: MutableList<Card> = mutableListOf()
        for (proxy in sourceToProxyMap) {
            val card = proxy.getCard(artistName)
            if(card !is EmptyCard)
                cardList.add(card)
        }
        return cardList
    }

}