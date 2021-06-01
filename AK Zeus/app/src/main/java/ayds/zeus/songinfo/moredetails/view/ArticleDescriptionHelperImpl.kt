package ayds.zeus.songinfo.moredetails.view

import java.util.*

interface ArticleDescriptionHelper {
    fun getTextToHtml(text: String, term: String): String
}

internal class ArticleDescriptionHelperImpl : ArticleDescriptionHelper {

    override fun getTextToHtml(text: String, term: String) = StringBuilder().apply{
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
}