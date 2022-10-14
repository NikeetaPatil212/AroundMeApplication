package com.example.aroundmeapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aroundmeapplication.R
import com.example.aroundmeapplication.dataclass.RestaurantData
import kotlinx.android.synthetic.main.item_restaurants.view.*

class RestaurantAdapter(  val mList: ArrayList<RestaurantData>) : RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    private lateinit var mListener: OnItemClickListener

 /*   private lateinit var nListener: OnItemClickListener
    private val list = mList*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_restaurants, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        holder.imgCircle.setImageResource(ItemsViewModel.image)

        // sets the text to the textview from our itemHolder class
        holder.tvItemName.text = ItemsViewModel.Name
        holder.tvDescription.text = ItemsViewModel.address
        holder.ivLocation.setOnClickListener() {
            notifyDataSetChanged()
            mListener.onItemclick(position, locationdata = ItemsViewModel)

        }
        holder.itemView.imgResWishList.setOnClickListener {
            ClickListener?.let {
                it(ItemsViewModel)
            }
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imgCircle: ImageView = itemView.findViewById(R.id.imgCircle)
        val tvItemName: TextView = itemView.findViewById(R.id.tvItemName)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val ivLocation: ImageView = itemView.findViewById(R.id.ivLocation)
    }

    interface OnItemClickListener {
        fun onItemclick(
            position: Int, locationdata: RestaurantData
        )
    }

    private var ClickListener: ((RestaurantData) -> Unit)? = null

    fun setOnItemClickListener(listener: (RestaurantData) -> Unit) {
        ClickListener = listener
    }

    fun ItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }
}