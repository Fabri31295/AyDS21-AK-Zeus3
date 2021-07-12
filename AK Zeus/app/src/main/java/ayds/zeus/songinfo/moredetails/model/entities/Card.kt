package ayds.zeus.songinfo.moredetails.model.entities

open class Card(
        val info: String,
        val url: String,
        val logoUrl: String,
        val source: Source,
        var isLocallyStored: Boolean = false
)

class EmptyCard : Card(
    "No results found",
    "",
    "https://image.freepik.com/vector-gratis/pagina-error-404-distorsion_23-2148105404.jpg",
    Source.EMPTY,
    false,
)