package ayds.zeus.songinfo.moredetails.view


import ayds.zeus3.wikipedia.Card
import ayds.zeus3.wikipedia.EmptyCard
import java.util.*

private const val PREFIX = "[*]"

interface CardDescriptionHelper {
    fun getCardInfoText(card: Card = EmptyCard, artistName: String): String = ""
}

internal class CardDescriptionHelperImpl : CardDescriptionHelper {

    override fun getCardInfoText(card: Card, artistName: String): String =
        getTextToHtml(
            getTextWithPrefix(card.info, card.isLocallyStoraged),
            artistName)

    private fun getTextToHtml(text: String, term: String) = StringBuilder().apply{
        append("<html><div width=400>")
        append("<font face=\"arial\">")
        append(textWithBold(text,term))
        append("</font></div></html>")
    } .toString()
    
    private fun textWithBold(text: String, term: String): String = 
        text
            .replace("'", " ")
            .replace("\n", "<br>")
            .replace("(?i)" + term.toRegex(), "<b>" + term.toUpperCase(Locale.ROOT) + "</b>")

    private fun getTextWithPrefix(text: String, wPrefix: Boolean) =
        "${if (wPrefix) PREFIX else ""} $text"
}