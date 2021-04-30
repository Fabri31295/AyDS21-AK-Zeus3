package ayds.zeus.songinfo.home.model.entities

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

    private fun dateWithDayPrecision(date: String) : String {
        val dateArray = date.split('-')
        return "${dateArray[2]}/${dateArray[1]}/${dateArray[0]}"
    }

    private fun dateWithMonthPrecision(date: String) : String =
        getMonthName(date.split('-')[1])

        private fun getMonthName(month: String) = when(month){
        "1" -> "January"
        "2" -> "February"
        "3" -> "March"
        "4" -> "April"
        "5" -> "May"
        "6" -> "June"
        "7" -> "July"
        "8" -> "August"
        "9" -> "September"
        "10" -> "October"
        "11" -> "November"
        "12" -> "December"
        else -> "*Invalid month number*"
    }

    private fun dateWithYearPrecision(date: String) : String {
        val year = date.split('-')[0].toInt()
        return "$year (${
            if (checkLeapYear(year)) "Leap year" 
            else "Not a leap year"})"
    }

        private fun checkLeapYear(year: Int): Boolean {
            return ((year % 400) == 0)
                    || (((year % 4) == 0) && ((year % 100) != 0))
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