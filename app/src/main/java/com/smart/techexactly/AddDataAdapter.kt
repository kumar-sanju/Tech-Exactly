package com.smart.techexactly

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AddDataAdapter: RecyclerView.Adapter<AddDataAdapter.AllDataViewHolder>(){

    var mList: ArrayList<AllData> = ArrayList()
    var mListNew: ArrayList<AllData> = ArrayList()

    fun updateArrayList(mList: ArrayList<AllData>){
        this.mList.clear()
        this.mList = mList
        this.mListNew = mList
        notifyDataSetChanged()
    }

    fun setFilter(query: String) {
        var filteredList = ArrayList<AllData>()
        var newfilteredList = mListNew

        if (query == ""){
            updateArrayList(newfilteredList)
        }
        else{
            for (item in mList) {
                if (item.title.lowercase().contains(query.lowercase())) {
                    filteredList.add(item)
                }
            }
            this.mList = filteredList
            notifyDataSetChanged()
        }
    }

    inner class AllDataViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val logo: ImageView = itemView.findViewById(R.id.image)
        val title: TextView = itemView.findViewById(R.id.textView)
        val switchView: Switch = itemView.findViewById(R.id.switchView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllDataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_list, parent, false)
        return AllDataViewHolder(view)
    }

    override fun onBindViewHolder(holder: AllDataViewHolder, position: Int) {
        holder.logo.setImageResource(mList[position].logo)
        holder.title.text = mList[position].title
        when(mList[position].switch) {
            true -> {holder.switchView.setChecked(true)}
            false -> {holder.switchView.setChecked(false)}
            null -> {holder.switchView.setChecked(false)}
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

}
