package ayds.zeus.songinfo.moredetails.model.repository

import android.util.Log
import ayds.zeus.songinfo.moredetails.model.broker.Broker
import ayds.zeus.songinfo.moredetails.model.entities.Card
import ayds.zeus.songinfo.moredetails.model.entities.EmptyCard
import ayds.zeus.songinfo.moredetails.model.repository.local.CardLocalStorage

interface CardRepository {
    fun getCardList(artistName: String): List<Card>
}

internal class CardRepositoryImpl(
        private val cardLocalStorage: CardLocalStorage,
        private val broker: Broker
) : CardRepository {

    override fun getCardList(artistName: String): List<Card> {
        var artistCard: Card? = cardLocalStorage.getCard(artistName)
        var cardList: List<Card> = mutableListOf()
        when {
            artistCard != null -> markCardAsLocal(artistCard)
            else -> {
                try {
                    cardList = broker.getCardList(artistName)
                    for(card in cardList)
                        cardLocalStorage.saveCard(card,artistName)
                } catch (e: Exception) {
                    Log.w("Card", "ERROR : $e")
                }
            }
        }
        return cardList
    }

    private fun markCardAsLocal(cardInfo: Card) {
        cardInfo.isLocallyStored = true
    }
}