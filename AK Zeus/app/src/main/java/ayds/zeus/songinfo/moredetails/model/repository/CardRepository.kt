package ayds.zeus.songinfo.moredetails.model.repository

import android.util.Log
import ayds.zeus.songinfo.moredetails.model.repository.broker.Broker
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
        var cardList: List<Card> = cardLocalStorage.getCardList(artistName)
        when {
            cardList.isNotEmpty() ->{
                for (card in cardList)
                    markCardAsLocal(card)
                return cardList+broker.getMissingCards(cardList, artistName)
            }
            else -> {
                try {
                    cardList = broker.getCardList(artistName)
                    for (card in cardList) {
                        cardLocalStorage.saveCard(card, artistName)
                    }
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