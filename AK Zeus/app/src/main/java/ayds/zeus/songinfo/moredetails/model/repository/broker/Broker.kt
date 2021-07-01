package ayds.zeus.songinfo.moredetails.model.repository.broker

import ayds.zeus.songinfo.moredetails.model.repository.broker.proxies.Proxy
import ayds.zeus.songinfo.moredetails.model.entities.Card
import ayds.zeus.songinfo.moredetails.model.entities.EmptyCard
import ayds.zeus.songinfo.moredetails.model.entities.Source

interface Broker {
    fun getCardList(artistName: String): List<Card>
    fun getMissingCards(listCard: List<Card>, artistName: String): List<Card>
}

class BrokerImpl(private val sourceToProxyMap: Map<Source,Proxy>) : Broker {

    override fun getCardList(artistName: String): List<Card> {
        val cardList: MutableList<Card> = mutableListOf()
        for (proxy in sourceToProxyMap.values) {
            val card = proxy.getCard(artistName)
            if(card !is EmptyCard)
                cardList.add(card)
        }
        return cardList
    }

    override fun getMissingCards(listCard: List<Card>, artistName: String): List<Card> {
        val missingSourceList: MutableList<Source> = mutableListOf()
        for (key in sourceToProxyMap.keys){
            if(!listCard.any{it.source == key})
                missingSourceList.add(key)
        }
        val cardList: MutableList<Card> = mutableListOf()
        for (source in missingSourceList)
            sourceToProxyMap[source]?.let { cardList.add(it.getCard(artistName)) }
        return cardList
    }


}