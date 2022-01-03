package com.ims.imdb_kotlin.listeners

import com.ims.imdb_kotlin.models.Result


interface BookMarkStatusesListener {
    fun onItemChanged(result: Result?)
    fun onAllCleared(size: Int)
}