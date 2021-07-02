package ayds.zeus.songinfo.moredetails.model.repository.broker.proxies

import android.util.Log
import ayds.apolo2.LastFM.LastFMAPIArtistService
import ayds.apolo2.LastFM.entities.InfoArtist
import ayds.zeus.songinfo.moredetails.model.entities.Card
import ayds.zeus.songinfo.moredetails.model.entities.EmptyCard
import ayds.zeus.songinfo.moredetails.model.entities.Source

internal class LastFMProxy(private val service: LastFMAPIArtistService) : Proxy {

    override fun getCard(artistName: String): Card {
        var card: Card = EmptyCard()
        try {
            card = service.getArtist(artistName).toCard()
        } catch (e: Exception) {
            Log.w("Card", "ERROR : $e")
        }
        return card
    }

    private fun InfoArtist?.toCard() =
        when {
            this == null -> EmptyCard()
            else -> Card(description, infoUrl, sourceLogoUrl, Source.LASTFM)
        }
}