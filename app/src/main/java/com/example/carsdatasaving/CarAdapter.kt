package com.example.carsdatasaving

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carsdatasaving.db.Car

class CarAdapter(val list: MutableList<Car>, val clickListener:carClickListener) :
    RecyclerView.Adapter<CarAdapter.CarHolder>() {


    inner class CarHolder (item : View): RecyclerView.ViewHolder(item){

        var title: TextView = itemView.findViewById(R.id.title)
        var descr: TextView = itemView.findViewById(R.id.description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.show,parent,false)
        return CarHolder(view)
    }

    override fun onBindViewHolder(holder: CarHolder, position: Int) {
        holder.itemView.apply {
            holder.title.text = list[position].model
            holder.descr.text = list[position].price.toString() + "$"
            holder.itemView.setOnClickListener {
                clickListener.fonItemClick(position)
            }

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface carClickListener {
        fun fonItemClick(position: Int)

    }

}