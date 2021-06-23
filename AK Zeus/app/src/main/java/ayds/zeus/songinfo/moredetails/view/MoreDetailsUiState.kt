package ayds.zeus.songinfo.moredetails.view
import ayds.zeus.songinfo.moredetails.model.entities.Source

data class MoreDetailsUiState(
    val artistName: String = "",
    val urlString: String = "",
    val cardInfo: String = "",
    val actionsEnabled: Boolean = false,
    val urlLogoImage: String = "",
    val source: Source = Source.EMPTY
)