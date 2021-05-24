package ayds.zeus.songinfo.moredetails.view

sealed class MoreDetailsUiEvent{
    object ShowArtistInfo: MoreDetailsUiEvent()
    object OpenWikipediaUrl: MoreDetailsUiEvent()
}