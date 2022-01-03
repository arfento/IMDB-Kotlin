package com.ims.imdb_kotlin.listeners

import java.io.Serializable

class BookMarkListenerObject(
    listener: BookMarkStatusesListener
) : Serializable {
    var listener: BookMarkStatusesListener = listener
}