package ayds.zeus.songinfo.moredetails.model.entities

interface Card {
    val info: String
    val url: String
    val sourceLogo: String
    val source: Int
    var isLocallyStoraged: Boolean
}

data class WikipediaCard(
    override val info: String,
    override val url: String,
    override val sourceLogo: String = "https://upload.wikimedia.org/wikipedia/commons/8/8c/Wikipedia-logo-v2-es.png",
    override val source: Int = 1,
    override var isLocallyStoraged: Boolean = false
) : Card

object EmptyCard : Card {
    override val info: String = ""
    override val url: String = ""
    override val sourceLogo: String = ""
    override val source: Int = -1
    override var isLocallyStoraged: Boolean = false
}