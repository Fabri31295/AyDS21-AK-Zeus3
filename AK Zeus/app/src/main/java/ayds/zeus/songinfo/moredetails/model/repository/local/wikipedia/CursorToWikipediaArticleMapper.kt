package ayds.zeus.songinfo.moredetails.model.repository.local.wikipedia

import android.database.Cursor
import ayds.zeus.songinfo.moredetails.model.entities.WikipediaArticle
import java.sql.SQLException

interface CursorToWikipediaArticleMapper {
    fun map(cursor: Cursor): WikipediaArticle?
}

internal class CursorToWikipediaArticleMapperImpl : CursorToWikipediaArticleMapper {

    override fun map(cursor: Cursor): WikipediaArticle? =
        try {
            with(cursor) {
                if (moveToNext()) {
                    WikipediaArticle(
                        info = getString(getColumnIndexOrThrow(INFO_COLUMN)),
                        url = getString(getColumnIndexOrThrow(URL_COLUMN))
                    )
                } else {
                    null
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
            null
        }

}