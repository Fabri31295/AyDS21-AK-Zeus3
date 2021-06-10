package ayds.zeus.songinfo.moredetails.model.entities

interface Card {
    val info: String
    val url: String
    val logo_url: String
    val source: Int
    var isLocallyStoraged: Boolean
}

data class CardImpl(
    override val info: String,
    override val url: String,
    override val logo_url: String,
    override val source: Int,
    override var isLocallyStoraged: Boolean = false
) : Card

object EmptyCard : Card {
    override val info: String = ""
    override val url: String = ""
    override val logo_url: String = ""
    override val source: Int = -1
    override var isLocallyStoraged: Boolean = false
}