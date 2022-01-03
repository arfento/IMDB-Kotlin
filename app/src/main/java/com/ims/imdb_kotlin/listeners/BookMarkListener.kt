package com.ims.imdb_kotlin.listeners

import com.ims.imdb_kotlin.models.Result


interface BookMarkListener {
    fun onBookMarkClicked(position: Int, result: Result?)
}