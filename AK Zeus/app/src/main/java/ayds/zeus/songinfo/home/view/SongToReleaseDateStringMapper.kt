package ayds.zeus.songinfo.home.view

import ayds.zeus.songinfo.home.model.entities.Song

interface SongToReleaseDateStringMapper {
    fun formatReleaseDate(): String
}

internal class PrecisionDayMapper(private val song: Song): SongToReleaseDateStringMapper {
    override fun formatReleaseDate(): String {
        val dateArray = song.releaseDate.split('-')
        val day = dateArray[2]
        val month = dateArray[1]
        val year = dateArray[0]
        return "$day/$month/$year"
    }
}

internal class PrecisionMonthMapper(private val song: Song): SongToReleaseDateStringMapper {

    override fun formatReleaseDate(): String {
        val dateArray = song.releaseDate.split('-')
        val month = dateArray[1]
        val year = dateArray[0]
        return "${getMonthName(month)}, $year"
    }

    private fun getMonthName(month: String) = when(month){
        "01" -> "January"
        "02" -> "February"
        "03" -> "March"
        "04" -> "April"
        "05" -> "May"
        "06" -> "June"
        "07" -> "July"
        "08" -> "August"
        "09" -> "September"
        "10" -> "October"
        "11" -> "November"
        "12" -> "December"
        else -> throw Exception("*Invalid month number*")
    }
}

internal class PrecisionYearMapper(private val song: Song): SongToReleaseDateStringMapper {
    override fun formatReleaseDate(): String {
        val year = song.releaseDate.toInt()
        return "$year (${
            if (checkLeapYear(year)) "Leap year"
            else "Not a leap year"})"
    }
    private fun checkLeapYear(year: Int): Boolean {
        return ((year % 400) == 0) || (((year % 4) == 0) && ((year % 100) != 0))
    }
}

internal class PrecisionEmptyMapper(): SongToReleaseDateStringMapper {
    override fun formatReleaseDate(): String =  ""
}