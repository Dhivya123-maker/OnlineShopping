package com.example.onlineshopping

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class RvAdapter(ctx: Context, private val dataModelArrayList: ArrayList<DataModel>) :
    RecyclerView.Adapter<RvAdapter.MyViewHolder>() {

     var context: Context? = null


    private val inflater: LayoutInflater

    init {

        inflater = LayoutInflater.from(ctx)
    }
    private lateinit var mListener : onItemClickListener
    private lateinit var mListener1 : onItemClickListener1
    interface onItemClickListener{
        fun onItemClick(position: Int)

    }
    interface onItemClickListener1{
        fun onItemClick1(position: Int)

    }
    fun setOnItemClickListener(listener : onItemClickListener){
        mListener = listener
    }
    fun setOnItemClickListener1(listener1 : onItemClickListener1){
        mListener1 = listener1
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvAdapter.MyViewHolder {

        val view = inflater.inflate(R.layout.adapter_shop, parent, false)

        return MyViewHolder(view,mListener,mListener1)
    }

    override fun onBindViewHolder(holder: RvAdapter.MyViewHolder, position: Int) {

        Picasso.get().load(dataModelArrayList[position].getImages()).into(holder.image)
        holder.name.setText(dataModelArrayList[position].getNames())
        holder.price.setText(dataModelArrayList[position].getPrices())



    }

    override fun getItemCount(): Int {
        return dataModelArrayList.size
    }

    inner class MyViewHolder(itemView: View,listener: onItemClickListener,listener1: onItemClickListener1) : RecyclerView.ViewHolder(itemView) {


        var name: TextView
        var price: TextView
        var image: ImageView
        var button : Button
        var view_btn : TextView


        init {
            name = itemView.findViewById<View>(R.id.text_view1) as TextView
             price = itemView.findViewById<View>(R.id.text_view2) as TextView
            image = itemView.findViewById<View>(R.id.image_view) as ImageView
            button = itemView.findViewById(R.id.button) as Button
            view_btn = itemView.findViewById(R.id.view_cart_btn) as TextView


            button.setOnClickListener {
                val position = adapterPosition

                    listener.onItemClick(position)
                    button.visibility = View.GONE
                    view_btn.visibility = View.VISIBLE

            }


      view_btn.setOnClickListener {
                listener1.onItemClick1(adapterPosition)

            }

        }



    }
}