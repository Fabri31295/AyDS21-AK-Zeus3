package ayds.zeus.songinfo.home.model.repository.external.spotify

import ayds.zeus.songinfo.home.model.entities.SpotifySong

interface SpotifyTrackService {

    fun getSong(title: String): SpotifySong?
}