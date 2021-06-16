package ayds.zeus.songinfo.moredetails.model.repository.local.wikipedia

import android.database.Cursor
import ayds.zeus3.wikipedia.ArticleImpl
import java.sql.SQLException

interface CursorToCardMapper {
    fun map(cursor: Cursor): ArticleImpl?
}

internal class CursorToCardMapperImpl : CursorToCardMapper {

    override fun map(cursor: Cursor): ArticleImpl? =
        try {
            with(cursor) {
                if (moveToNext()) {
                    ArticleImpl(
                        info = getString(getColumnIndexOrThrow(INFO_COLUMN)),
                        url = getString(getColumnIndexOrThrow(URL_COLUMN)),
                        logo_url = getString(getColumnIndexOrThrow(LOGO_URL_COLUMN)),
                        source = getInt(getColumnIndexOrThrow(SOURCE_COLUMN)),
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