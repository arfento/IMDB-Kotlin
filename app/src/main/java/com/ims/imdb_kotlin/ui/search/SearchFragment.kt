package com.ims.imdb_kotlin.ui.search

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ims.imdb_kotlin.R
import com.ims.imdb_kotlin.models.Result
import com.ims.imdb_kotlin.adapters.SearchAdapter
import com.ims.imdb_kotlin.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchAdapter.onSearchResultClickListener{

    private lateinit var binding : FragmentSearchBinding
    private lateinit var results : ArrayList<Result>
    private val searchViewModel : SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        results = ArrayList()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        binding = FragmentSearchBinding.bind(view)
        bindView()
        return  binding.root
    }

    private fun bindView() {
        val lm = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.searchResults.layoutManager = lm
        val adapter = SearchAdapter(this, results)
        binding.searchResults.adapter = adapter
        val itemDecoration : RecyclerView.ItemDecoration =
            DividerItemDecoration(requireContext(), LinearLayout.VERTICAL)
        searchViewModel.searchResultMutableLiveData.observe(viewLifecycleOwner, { searchResult ->
            if (searchResult != null){
                if (searchResult.results!=null && searchResult.results!!.isEmpty()){
                    binding.searchResults.visibility = View.GONE
                    binding.noResultsLayout.visibility = View.VISIBLE
                }else{
                    binding.searchResults.visibility = View.VISIBLE
                    binding.noResultsLayout.visibility = View.GONE
                }
                results.clear()
                searchResult.results?.let{
                    results.addAll(it)
                }
                adapter.notifyDataSetChanged()
            }
        })

        binding.backButton.setOnClickListener { closeFragment() }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                if (query != null && !TextUtils.isEmpty(query)){
                    searchViewModel.getSearchResults(query)
                }else{
                    Toast.makeText(requireContext(), "Query cannot be empty", Toast.LENGTH_SHORT).show()
                }
                return true
            }


            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }
        })
    }

    override fun onResultClick(result: Result?) {
        if (result!=null){
            val action : NavDirections =
                SearchFragmentDirections.actionSearchFragmentToDetailFragment(result)
            Navigation.findNavController(binding.root).navigate(action)
        }
    }

    private fun closeFragment() {
        Navigation.findNavController(binding.root).popBackStack()
    }

}