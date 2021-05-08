package ayds.zeus.songinfo.moredetails.fulllogic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.sql.*


class DataBase(private val context: Context): SQLiteOpenHelper(context,"dictionary.db", null, 1) {
    fun testDB(){
        var connection:Connection? = null
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db")
            val statement = connection.createStatement()
            statement.queryTimeout = 30
            val artistsResultSet = statement.executeQuery("select * from artists")
            while(artistsResultSet.next()){
                println("id = ${artistsResultSet.getInt("id")}")
                println("artist = ${artistsResultSet.getString("artist")}")
                println("info = ${artistsResultSet.getString("info")}")
                println("source = ${artistsResultSet.getString("source")}")
            }
        }catch (e: SQLException){
            println(e.message)
        }finally{
            try{
                connection?.close()
            }catch (e: SQLException){
                println(e.message)
            }
        }
    }

    fun saveArtist(dbHelper: DataBase, artist: String, info: String ){
        val dataBase = dbHelper.writableDatabase;
    }

    override fun onCreate(db: SQLiteDatabase){

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int){

    }
}