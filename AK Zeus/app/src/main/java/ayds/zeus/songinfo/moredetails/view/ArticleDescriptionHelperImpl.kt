package ayds.zeus.songinfo.moredetails.view

import ayds.zeus.songinfo.moredetails.model.entities.Article
import ayds.zeus.songinfo.moredetails.model.entities.EmptyArticle
import java.util.*

private const val PREFIX = "[*]"

interface ArticleDescriptionHelper {
    fun getArticleInfoText(article: Article = EmptyArticle, artistName: String): String = ""
}

internal class ArticleDescriptionHelperImpl : ArticleDescriptionHelper {

    override fun getArticleInfoText(article: Article, artistName: String): String =
        getTextToHtml(
            getTextWithPrefix(article.info, article.isLocallyStoraged),
            artistName)

    private fun getTextToHtml(text: String, term: String) = StringBuilder().apply{
        append("<html><div width=400>")
        append("<font face=\"arial\">")
        append(textWithBold(text,term))
        append("</font></div></html>")
    } .toString()
    
    private fun textWithBold(text: String, term: String): String = 
        text
            .replace("'", " ")
            .replace("\n", "<br>")
            .replace("(?i)" + term.toRegex(), "<b>" + term.toUpperCase(Locale.ROOT) + "</b>")

    private fun getTextWithPrefix(text: String, wPrefix: Boolean) =
        if (wPrefix) PREFIX + text
        else text
}