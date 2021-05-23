package ayds.zeus.songinfo.moredetails.fulllogic.view

sealed class MoreDetailsUiEvent{
    object ShowArtistInfo: MoreDetailsUiEvent()
    object OpenWikipediaUrl: MoreDetailsUiEvent()
}