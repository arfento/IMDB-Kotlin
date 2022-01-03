package com.ims.imdb_kotlin.ui.bookmark

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ims.imdb_kotlin.db.DatabaseService
import com.ims.imdb_kotlin.listeners.BookMarkListener
import com.ims.imdb_kotlin.models.Result
import com.ims.imdb_kotlin.network.ApiService
import com.ims.imdb_kotlin.utils.Helper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class BookMarkViewModel @Inject constructor(
    private val apiService: ApiService,
    private val helper: Helper,
    private val databaseService: DatabaseService
) : ViewModel() {
    val bookMarkResult = MutableLiveData<List<Result>>()
    val msg = MutableLiveData<String>()
    fun clearBookMarks() {
        viewModelScope.launch {
            try {
                coroutineScope {
                    try {
                        val resultDao = databaseService.resultDao()
                        resultDao.clearBookMarks()
                        getData()
                    }catch (e: Exception){
                        msg.postValue("Something went wrong")
                        
                    }
                }
            } catch (e: Exception) {
                Log.i("error", "clearBookMarks: ${e.message.toString()}")
                msg.postValue("Something went wrong")
            }
        }
    }

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            try{
                val results = databaseService.resultDao().bookMarkedResults()
                Log.i("Bookmark results", "getData: ${results.toString()}")
                bookMarkResult.postValue(results)
            }catch (e: Exception){
                Log.i("get data error bookmark", "getData: ${e.message.toString()}")
            }
        }

    }

    fun setBookMarkStatus(result: Result?, position: Int, listener: BookMarkListener?) {
        viewModelScope.launch {
            try{
                if(result!=null && listener!=null){
                    result.bookmarked=!result.bookmarked
                    databaseService.resultDao().updateBookMarked(result)
                    listener.onBookMarkClicked(position,result)
                }
            }
            catch (e:Exception){
                msg.postValue("Something went wrong")
                Log.i("get data error bookmark",e.message.toString())
            }
        }
    }
}