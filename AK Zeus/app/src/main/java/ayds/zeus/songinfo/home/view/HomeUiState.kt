package ayds.zeus.songinfo.home.view

data class HomeUiState(
    val songId: String = "",
    val searchTerm: String = "",
    val songDescription: String = "",
    val songImageUrl: String = DEFAULT_IMAGE,
    val songUrl: String = "",
    val actionsEnabled: Boolean = false,
) {

    companion object {
        const val DEFAULT_IMAGE =
            "https://comicvine1.cbsistatic.com/uploads/original/11134/111341899/6327456-zeus%20god%20of%20war.jpg"
    }
}