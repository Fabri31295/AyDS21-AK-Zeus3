package ayds.zeus.songinfo.home.model.entities

import ayds.zeus.songinfo.utils.date.getDateWithDayPrecision
import ayds.zeus.songinfo.utils.date.getDateWithMonthPrecision
import ayds.zeus.songinfo.utils.date.getDateWithYearPrecision

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
        "day" -> getDateWithDayPrecision(releaseDate)
        "month" -> getDateWithMonthPrecision(releaseDate)
        "year" -> getDateWithYearPrecision(releaseDate)
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