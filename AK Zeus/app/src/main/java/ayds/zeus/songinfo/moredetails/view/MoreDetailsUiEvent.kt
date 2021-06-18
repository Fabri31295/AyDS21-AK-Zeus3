package ayds.zeus.songinfo.moredetails.view

sealed class MoreDetailsUiEvent {
    object ShowCardInfo : MoreDetailsUiEvent()
    object OpenFullArticle : MoreDetailsUiEvent()
}