package ayds.zeus.songinfo.moredetails.model.repository

import android.util.Log
import ayds.zeus.songinfo.moredetails.model.repository.entities.Card
import ayds.zeus.songinfo.moredetails.model.repository.local.CardLocalStorage
import ayds.zeus3.wikipedia.WikipediaService

interface CardRepository {
    fun getCard(artistName: String): Card?
}

internal class CardRepositoryImpl(
        private val cardLocalStorage: CardLocalStorage,
        private val wikipediaService: WikipediaService,
        private val articleToCardMapper: ArticleToCardMapper,
) : CardRepository {

    override fun getCard(artistName: String): Card? {
        var cardInfo: Card? = cardLocalStorage.getCard(artistName)

        when {
            cardInfo != null -> markCardAsLocal(cardInfo)
            else -> {
                try {
                    val article = wikipediaService.getArticle(artistName)
                    article?.let { cardInfo = articleToCardMapper.map(it) }
                    cardInfo?.let { cardLocalStorage.saveCard(it, artistName) }
                } catch (e: Exception) {
                    Log.w("Card", "ERROR : $e")
                }
            }
        }
        return cardInfo
    }

    private fun markCardAsLocal(cardInfo: Card) {
        cardInfo.isLocallyStored = true
    }
}