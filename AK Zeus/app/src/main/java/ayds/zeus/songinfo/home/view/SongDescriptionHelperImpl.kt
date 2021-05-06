package ayds.zeus.songinfo.home.view

import ayds.zeus.songinfo.home.model.entities.EmptySong
import ayds.zeus.songinfo.home.model.entities.Song
import ayds.zeus.songinfo.home.model.entities.SpotifySong

interface SongDescriptionHelper {
    fun getSongDescriptionText(song: Song = EmptySong): String
}

internal class SongDescriptionHelperImpl(private val factorySongReleaseMapper: FactorySongReleaseDateMapper) : SongDescriptionHelper {

    override fun getSongDescriptionText(song: Song): String {
        return when (song) {
            is SpotifySong ->
                "${
                    "Song: ${song.songName} " +
                            if (song.isLocallyStoraged) "[*]" else ""
                } \n" +
                        "Artist: ${song.artistName}  \n" +
                        "Album: ${song.albumName}  \n" +
                        "Release Date:  ${factorySongReleaseMapper.get(song).formatReleaseDate()}"
            else -> "Song not found"
        }
    }

}