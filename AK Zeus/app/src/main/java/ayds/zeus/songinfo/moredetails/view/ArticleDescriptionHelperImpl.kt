package ayds.zeus.songinfo.moredetails.view

import java.util.*

interface ArticleDescriptionHelper {
    fun getTextToHtml(text: String, term: String): String
}

internal class ArticleDescriptionHelperImpl() : ArticleDescriptionHelper {

    override fun getTextToHtml(text: String, term: String): String {
        val builder = StringBuilder()
        builder.append("<html><div width=400>")
        builder.append("<font face=\"arial\">")
        val textWithBold = text
            .replace("'", " ")
            .replace("\n", "<br>")
            .replace("(?i)" + term.toRegex(), "<b>" + term.toUpperCase(Locale.ROOT) + "</b>")
        builder.append(textWithBold)
        builder.append("</font></div></html>")
        return builder.toString()
    }

}