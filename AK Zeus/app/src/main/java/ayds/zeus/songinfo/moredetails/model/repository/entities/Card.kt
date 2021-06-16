package ayds.zeus.songinfo.moredetails.model.repository.entities
import ayds.zeus.songinfo.moredetails.model.repository.Source

open class Card(
    val info: String,
    val url: String,
    val logoUrl: String,
    val source: Source,
    var isLocallyStoraged: Boolean
)

class EmptyCard : Card(
    "",
    "",
    "",
    Source.EMPTY,
    false,
)