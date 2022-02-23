package com.yulicahyani.eraport.ui.inputnilai.listmapel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yulicahyani.eraport.data.source.remote.response.ResultsMapel
import com.yulicahyani.eraport.databinding.ItemListMapelBinding

class ListMapelAdapter : RecyclerView.Adapter<ListMapelAdapter.MapelViewHolder>() {

    private val listData = ArrayList<ResultsMapel>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(mapel: ArrayList<ResultsMapel>) {
        listData.clear()
        listData.addAll(mapel)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapelViewHolder {
        val view = ItemListMapelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MapelViewHolder((view))
    }

    override fun onBindViewHolder(holder: MapelViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    inner  class MapelViewHolder (private val binding : ItemListMapelBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(mapel : ResultsMapel){
            binding.root.setOnClickListener {
                onItemClickCallback.onItemClicked(mapel)
            }
            binding.apply {
                tvNameMapel.text = mapel.nama_mapel
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ResultsMapel)
    }
}