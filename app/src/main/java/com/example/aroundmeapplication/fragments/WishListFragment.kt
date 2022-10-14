package com.example.aroundmeapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aroundmeapplication.R
import com.example.aroundmeapplication.adapter.RestaurantAdapter
import com.example.aroundmeapplication.database.RestaurantDatabase
import com.example.aroundmeapplication.dataclass.RestaurantData
import com.example.aroundmeapplication.factory.RestaurantFactory
import com.example.aroundmeapplication.repository.RestaurantRepository
import com.example.aroundmeapplication.viewmodel.RestaurantViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_wish_list.*

class WishListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wish_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = RestaurantRepository(RestaurantDatabase(requireContext()))
        val viewModelProviderFactory = RestaurantFactory(repository)
        val viewModel: RestaurantViewModel = ViewModelProvider(
            requireActivity(),
            viewModelProviderFactory
        ).get(RestaurantViewModel::class.java)


        viewModel.getData().observe(viewLifecycleOwner, Observer {
            var vadapter = RestaurantAdapter(it as ArrayList<RestaurantData>)
            wishListRecyclerView.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = vadapter

                val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
                    ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                ){
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        return true
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        val position = viewHolder.adapterPosition
                        val article = vadapter.mList[position]
                        viewModel.deleteRestaurantData(article)
                        Snackbar.make(view,"Restaurant Deleted Successfully", Snackbar.LENGTH_LONG).show()
                    }
                }

                ItemTouchHelper(itemTouchHelperCallback).apply {
                    attachToRecyclerView(wishListRecyclerView)
                }
            }

        })
    }
}