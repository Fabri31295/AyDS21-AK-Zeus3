package ayds.hera3.nytimes.entities

interface Article {
    val artistInfo: String
    val url: String
    val logoUrl: String
}

data class NYTimesArticle(
    override val artistInfo: String,
    override val url: String,
    override val logoUrl: String = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRVioI832nuYIXqzySD8cOXRZEcdlAj3KfxA62UEC4FhrHVe0f7oZXp3_mSFG7nIcUKhg&usqp=CAU"
) : Article

object EmptyArticle : Article {
    override val artistInfo: String = ""
    override val url: String = ""
    override val logoUrl: String = "https://upload.wikimedia.org/wikipedia/commons/5/59/Empty.png"
}