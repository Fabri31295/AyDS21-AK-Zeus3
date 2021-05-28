package ayds.zeus.songinfo.moredetails.model.entities

data class WikipediaArticle(
    val name: String,
    val info: String?,
    val url: String,
    var isLocallyStoraged:Boolean = false,
)