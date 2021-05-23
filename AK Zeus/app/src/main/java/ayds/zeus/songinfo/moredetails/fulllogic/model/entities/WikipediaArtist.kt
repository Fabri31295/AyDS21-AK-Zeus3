package ayds.zeus.songinfo.moredetails.fulllogic.model.entities

interface Artist{
    val id: String
    val name: String
    val info: String
    val source: String
}

data class WikipediaArtist(
        override val id: String,
        override val name: String,
        override val info: String,
        override val source: String
):Artist