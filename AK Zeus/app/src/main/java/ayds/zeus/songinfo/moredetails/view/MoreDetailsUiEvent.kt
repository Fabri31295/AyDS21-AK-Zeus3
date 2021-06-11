package ayds.zeus.songinfo.moredetails.view

sealed class MoreDetailsUiEvent {
    object ShowArticleInfo : MoreDetailsUiEvent()
    object OpenFullArticle : MoreDetailsUiEvent()
}