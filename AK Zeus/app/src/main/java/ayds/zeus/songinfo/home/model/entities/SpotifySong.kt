package ayds.zeus.songinfo.home.model.entities

import ayds.zeus.songinfo.utils.date.dateWithDayPrecision
import ayds.zeus.songinfo.utils.date.dateWithMonthPrecision
import ayds.zeus.songinfo.utils.date.dateWithYearPrecision

interface Song {
    val id: String
    val songName: String
    val artistName: String
    val albumName: String
    val releaseDate: String
    val spotifyUrl: String
    val imageUrl: String
    val releaseDatePrecision: String
    var isLocallyStoraged: Boolean
}

data class SpotifySong(
        override val id: String,
        override val songName: String,
        override val artistName: String,
        override val albumName: String,
        override val releaseDate: String,
        override val spotifyUrl: String,
        override val imageUrl: String,
        override val releaseDatePrecision: String,
        override var isLocallyStoraged: Boolean = false
) : Song {

    val releaseDateWithPrecision =
    when (releaseDatePrecision) {
        "day" -> dateWithDayPrecision(releaseDate)
        "month" -> dateWithMonthPrecision(releaseDate)
        "year" -> dateWithYearPrecision(releaseDate)
        else -> "*Invalid date precision*"
    }


}

object EmptySong : Song {
    override val id: String = ""
    override val songName: String = ""
    override val artistName: String = ""
    override val albumName: String = ""
    override val releaseDate: String = ""
    override val spotifyUrl: String = ""
    override val imageUrl: String = ""
    override val releaseDatePrecision: String = ""
    override var isLocallyStoraged: Boolean = false
}