package ayds.zeus.songinfo.utils.date

fun getDateWithDayPrecision(date: String) : String {
    val dateArray = date.split('-')
    return "${dateArray[2]}/${dateArray[1]}/${dateArray[0]}"
}

fun getDateWithMonthPrecision(date: String) : String {
    val dateArray = date.split('-')
    return "${getMonthName(dateArray[1])}, ${dateArray[0]}"
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
    else -> "*Invalid month number*"
}

fun getDateWithYearPrecision(date: String) : String {
    val year = date.split('-')[0].toInt()
    return "$year (${
        if (checkLeapYear(year)) "Leap year"
        else "Not a leap year"})"
}

private fun checkLeapYear(year: Int): Boolean {
    return ((year % 400) == 0) || (((year % 4) == 0) && ((year % 100) != 0))
}