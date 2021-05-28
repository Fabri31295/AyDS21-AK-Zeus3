package ayds.zeus.songinfo.moredetails.model.entities

interface Article {
    val info: String
    val url: String
    var isLocallyStoraged: Boolean
}

data class WikipediaArticle(
    override val name: String,
    override val info: String,
    override val url: String,
    override var isLocallyStoraged: Boolean = false
) : Article

object EmptyArticle : Article {
    override val name: String = ""
    override val info: String = ""
    override val url: String = ""
    override var isLocallyStoraged: Boolean = false
}