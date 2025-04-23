package com.example.mywiki

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Adapter(showMe: ArrayList<Modal>, private var listener:OnItemClicked) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    private val items: ArrayList<Modal> = showMe

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val title: TextView = itemView.findViewById(R.id.heading)
        val description: TextView = itemView.findViewById(R.id.sub_heading)
        val categ:TextView=itemView.findViewById(R.id.category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view= LayoutInflater.from(parent.context).inflate(R.layout.single_item, parent, false)
        val viewHolder=ViewHolder(view)
        view.setOnClickListener {
            listener.onclick(items[viewHolder.absoluteAdapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]
       //holder.title.text = currentItem.user
        holder.description.text = currentItem.description
        Glide.with(holder.itemView.context).load(currentItem.url).into(holder.image)
        holder.categ.text=currentItem.cat

    }
//    fun updateList(newList:List<Item>){
//        items.clear()
//        items.addAll(newList)
//        notifyDataSetChanged()
//    }

    override fun getItemCount(): Int {
        return items.size
    }
    fun interface OnItemClicked{
        fun onclick(item: Modal)
    }
}