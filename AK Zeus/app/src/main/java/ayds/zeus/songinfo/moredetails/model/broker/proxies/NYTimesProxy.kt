package ayds.zeus.songinfo.moredetails.model.broker.proxies

import ayds.zeus.songinfo.moredetails.model.entities.Card

internal interface NYTimesProxy{
    fun getCard(): Card
}