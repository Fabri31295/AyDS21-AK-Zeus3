package ayds.zeus.songinfo.moredetails.model.repository

import ayds.zeus.songinfo.moredetails.model.repository.broker.Broker
import ayds.zeus.songinfo.moredetails.model.entities.Card
import ayds.zeus.songinfo.moredetails.model.repository.local.CardLocalStorage

interface CardRepository {
    fun getCardList(artistName: String): List<Card>
}

internal class CardRepositoryImpl(
    private val cardLocalStorage: CardLocalStorage,
    private val broker: Broker
) : CardRepository {

    override fun getCardList(artistName: String): List<Card> {
        var cardList: List<Card> = cardLocalStorage.getCardList(artistName)
        when {
            cardList.isNotEmpty() ->
                for (card in cardList)
                    markCardAsLocal(card)
            else -> {
                cardList = broker.getCardList(artistName)
                for (card in cardList) {
                    cardLocalStorage.saveCard(card, artistName)
                }
            }
        }
        return cardList
    }

    private fun markCardAsLocal(cardInfo: Card) {
        cardInfo.isLocallyStored = true
    }
}