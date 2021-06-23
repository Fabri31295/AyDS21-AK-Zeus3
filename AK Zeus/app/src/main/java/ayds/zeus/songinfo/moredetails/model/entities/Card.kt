package ayds.zeus.songinfo.moredetails.model.entities

open class Card(
        val info: String,
        val url: String,
        val logoUrl: String,
        val source: Source,
        var isLocallyStored: Boolean = false
)

class EmptyCard : Card(
    "",
    "",
    "",
    Source.EMPTY,
    false,
)