package ayds.zeus.songinfo.home.view

import ayds.zeus.songinfo.home.model.entities.Song


interface FactorySongReleaseDateMapper {
    fun getMapSongReleaseDate(song:Song): SongToReleaseDateStringMapper
}

internal class FactorySongReleaseDateMapperImp: FactorySongReleaseDateMapper{
    override fun getMapSongReleaseDate(song: Song): SongToReleaseDateStringMapper =
        when (song.releaseDatePrecision) {
            "day" -> PrecisionDayMapper(song)
            "month" -> PrecisionMonthMapper(song)
            "year" -> PrecisionYearMapper(song)
            else -> PrecisionEmptyMapper()
        }

}