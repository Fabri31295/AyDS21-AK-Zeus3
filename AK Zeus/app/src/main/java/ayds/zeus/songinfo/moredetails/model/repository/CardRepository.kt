package ayds.zeus.songinfo.moredetails.model.repository

import android.util.Log
import ayds.zeus3.wikipedia.Card
import ayds.zeus.songinfo.moredetails.model.repository.local.wikipedia.CardLocalStorage
import ayds.zeus3.wikipedia.EmptyCard
import ayds.zeus3.wikipedia.WikipediaService

interface CardRepository {
    fun getCard(artistName: String): Card
}

internal class CardRepositoryImpl(
    private val cardLocalStorage: CardLocalStorage,
    private val wikipediaService: WikipediaService
) : CardRepository {

    override fun getCard(artistName: String): Card {
        var cardInfo = cardLocalStorage.getCard(artistName)

        when {
            cardInfo != null -> markCardAsLocal(cardInfo)
            else -> {
                try {
                    cardInfo = wikipediaService.getCard(artistName)
                    if (cardInfo != null)
                        cardLocalStorage.saveCard(cardInfo, artistName)
                } catch (e: Exception) {
                    Log.w("Wikipedia card", "ERROR : $e")
                }
            }
        }
        return cardInfo ?: EmptyCard
    }

    private fun markCardAsLocal(cardInfo: Card) {
        cardInfo.isLocallyStoraged = true
    }
}