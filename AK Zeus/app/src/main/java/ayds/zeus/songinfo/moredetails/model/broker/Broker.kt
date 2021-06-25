package ayds.zeus.songinfo.moredetails.model.broker

import ayds.zeus.songinfo.moredetails.model.entities.Card

interface Broker {
    fun getCardList(): List<Card>
}

class BrokerImpl : Broker {
    override fun getCardList(): List<Card> {
        TODO("Not yet implemented")
    }

}