package ayds.zeus.songinfo.utils

import ayds.zeus.songinfo.utils.view.ImageLoader
import ayds.zeus.songinfo.utils.view.ImageLoaderImpl
import com.squareup.picasso.Picasso

object UtilsModule {

    val imageLoader: ImageLoader = ImageLoaderImpl(Picasso.get())
}