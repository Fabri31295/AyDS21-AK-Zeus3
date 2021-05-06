package ayds.zeus.songinfo.home.view

import ayds.zeus.songinfo.home.model.entities.Song

interface FactorySongReleaseDateMapper {
    fun get(song: Song): SongToReleaseDateStringMapper
}

internal class FactorySongReleaseDateMapperImp: FactorySongReleaseDateMapper {
    override fun get(song: Song): SongToReleaseDateStringMapper =
        when (song.releaseDatePrecision) {
            "day" -> PrecisionDayMapper(song)
            "month" -> PrecisionMonthMapper(song)
            "year" -> PrecisionYearMapper(song)
            else -> PrecisionEmptyMapper()
        }
}