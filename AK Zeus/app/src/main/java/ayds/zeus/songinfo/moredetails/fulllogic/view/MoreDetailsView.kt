package ayds.zeus.songinfo.moredetails.fulllogic.view

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import ayds.zeus.songinfo.moredetails.fulllogic.WikipediaAPI
import com.squareup.picasso.RequestCreator
import retrofit2.Retrofit

interface FullLogicView{

}

class FullLogicActivity {
    private lateinit var artistDescriptionPane: TextView
    private lateinit var wikipediaImagePane: ImageView
    private lateinit var wikipediaImage: RequestCreator
    private lateinit var openUrlButton: Button
    private lateinit var retrofit: Retrofit
    private lateinit var wikipediaAPI: WikipediaAPI
}