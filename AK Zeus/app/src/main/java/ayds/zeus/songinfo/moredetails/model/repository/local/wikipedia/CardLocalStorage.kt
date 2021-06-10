package ayds.zeus.songinfo.moredetails.model.repository.local.wikipedia

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ayds.zeus.songinfo.moredetails.model.entities.CardImpl

private const val DATABASE_VERSION = 1
private const val DATABASE_NAME = "dictionary.db"

interface CardLocalStorage {
    fun saveArticle(card: CardImpl, artistName: String)
    fun getArticle(card: String): CardImpl?
}

internal class CardLocalStorageImpl(
    context: Context,
    private val cursorToCardMapper: CursorToCardMapper,
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION),
    CardLocalStorage {

    override fun saveArticle(card: CardImpl, artistName: String) {
        val contentValues = getArtistContentValues(artistName, card.info, card.url,card.source,card.logo_url)
        this.writableDatabase.insert(ARTISTS_TABLE, null, contentValues)
    }

    override fun getArticle(card: String): CardImpl? {
        val cursor = getNewArtistCursor(card)
        return cursorToCardMapper.map(cursor)
    }

    private fun getArtistContentValues(artist: String, info: String, url: String, source: Int, logoUrl: String) =
        ContentValues().apply {
            this.put(ARTIST_COLUMN, artist)
            this.put(INFO_COLUMN, info)
            this.put(URL_COLUMN, url)
            this.put(SOURCE_COLUMN, source)
            this.put(LOGO_URL_COLUMN,logoUrl)
        }

    private fun getNewArtistCursor(artist: String): Cursor {
        val dataBase = this.readableDatabase
        val projection = arrayOf(ID_COLUMN, ARTIST_COLUMN, INFO_COLUMN, URL_COLUMN, SOURCE_COLUMN, LOGO_URL_COLUMN)
        val selection = "$ARTIST_COLUMN = ?"
        val selectionArgs = arrayOf(artist)
        val sortOrder = "$ARTIST_COLUMN DESC"
        return dataBase.query(
            ARTISTS_TABLE,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_ARTISTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
}