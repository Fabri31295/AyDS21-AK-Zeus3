package ayds.zeus.songinfo.moredetails.model.repository.local.wikipedia

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ayds.zeus.songinfo.moredetails.model.entities.WikipediaArticle

private const val DATABASE_VERSION = 1
private const val DATABASE_NAME = "dictionary.db"

interface WikipediaLocalStorage {
    fun saveArticle(article: WikipediaArticle)
    fun getArticle(artist: String): WikipediaArticle?
}

internal class WikipediaLocalStorageImpl(
    context: Context,
    private val cursorToWikipediaArticleMapper: CursorToWikipediaArticleMapper,
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION),
    WikipediaLocalStorage {

    override fun saveArticle(article: WikipediaArticle) {
        val contentValues = getArtistContentValues(article.name, article.info, article.url)
        this.writableDatabase.insert(ARTISTS_TABLE, null, contentValues)
    }

    override fun getArticle(artist: String): WikipediaArticle? {
        val cursor = getNewArtistCursor(artist)
        return cursorToWikipediaArticleMapper.map(cursor)
    }

    private fun getArtistContentValues(artist: String, info: String, url: String) =
        ContentValues().apply {
            this.put(ARTIST_COLUMN, artist)
            this.put(INFO_COLUMN, info)
            this.put(URL_COLUMN, url)
            this.put(SOURCE_COLUMN, 1)
        }

    private fun getNewArtistCursor(artist: String): Cursor {
        val dataBase = this.readableDatabase
        val projection = arrayOf(ID_COLUMN, ARTIST_COLUMN, INFO_COLUMN, URL_COLUMN)
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