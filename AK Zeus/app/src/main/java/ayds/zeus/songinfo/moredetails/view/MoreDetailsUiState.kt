package ayds.zeus.songinfo.moredetails.view

data class MoreDetailsUiState(
    val artistName: String = "",
    val urlString: String = "",
    val articleInfo: String = "",
    val actionsEnabled: Boolean = false,
    val urlLogoImage: String = "",
    val source: Int = -1
)