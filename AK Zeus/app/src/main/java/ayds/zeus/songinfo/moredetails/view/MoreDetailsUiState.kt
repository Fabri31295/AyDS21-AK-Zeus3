package ayds.zeus.songinfo.moredetails.view

data class MoreDetailsUiState(
    val artistName: String = "",
    val urlString: String = "",
    val articleInfo: String = "",
    val actionsEnabled: Boolean = false
) {
    companion object{
        const val IMAGE_WIKIPEDIA =
            "https://upload.wikimedia.org/wikipedia/commons/8/8c/Wikipedia-logo-v2-es.png"
    }
}