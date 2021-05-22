package ayds.zeus.songinfo.moredetails.fulllogic.model.entities

interface Artist{
    val id: String
    val artist_name: String
    val artist_info: String
    val source: String
}

data class WikipediaArtist(
        override val id: String,
        override val artist_name: String,
        override val artist_info: String,
        override val source: String
):Artist