package com.ims.imdb_kotlin.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ims.imdb_kotlin.db.DatabaseService
import com.ims.imdb_kotlin.models.SearchResult
import com.ims.imdb_kotlin.network.ApiService
import com.ims.imdb_kotlin.utils.Helper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val apiService: ApiService,
    private val helper: Helper,
    private val databaseService: DatabaseService
) : ViewModel(){

    val searchResultMutableLiveData = MutableLiveData<SearchResult>()
    val msg = MutableLiveData<String>()
    val loading= MutableLiveData<Boolean>()

    init {
        loading.value = false
    }

    fun getSearchResults(query : String){
        if (helper.getConnection()){
            viewModelScope.launch {
                loading.postValue(true)
                try {
                    val res = apiService.getSearchResults(query, true)
                    searchResultMutableLiveData.postValue(res)
                }catch (e : Exception){
                    searchResultMutableLiveData.postValue(SearchResult())
                    msg.postValue("Something went wrong")
                }
                finally {
                    loading.postValue(false)
                }
            }
        }else{
            msg.postValue("No internet Connection")
        }
    }
}