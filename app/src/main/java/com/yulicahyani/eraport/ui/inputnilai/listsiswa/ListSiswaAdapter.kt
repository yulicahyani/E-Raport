package com.yulicahyani.eraport.ui.inputnilai.listsiswa

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yulicahyani.eraport.data.source.remote.response.ResultsSiswa
import com.yulicahyani.eraport.databinding.ItemListSiswaBinding


class ListSiswaAdapter : RecyclerView.Adapter<ListSiswaAdapter.SiswaViewHolder>() {

    private val listData = ArrayList<ResultsSiswa>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(siswas: ArrayList<ResultsSiswa>) {
        listData.clear()
        listData.addAll(siswas)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiswaViewHolder {
        val view = ItemListSiswaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SiswaViewHolder((view))
    }

    override fun onBindViewHolder(holder: SiswaViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    inner class SiswaViewHolder(private val binding: ItemListSiswaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(siswa: ResultsSiswa) {
            binding.root.setOnClickListener {
                onItemClickCallback.onItemClicked(siswa)
            }

            binding.apply {
                tvNameSiswa.text = siswa.nama_siswa
            }
        }

    }


    interface OnItemClickCallback {
        fun onItemClicked(data: ResultsSiswa)
    }
}