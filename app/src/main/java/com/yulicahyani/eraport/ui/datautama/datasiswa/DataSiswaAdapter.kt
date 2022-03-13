package com.yulicahyani.eraport.ui.datautama.datasiswa

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yulicahyani.eraport.data.source.remote.response.ResultsSiswa
import com.yulicahyani.eraport.databinding.ItemDataSiswaBinding

class DataSiswaAdapter : RecyclerView.Adapter<DataSiswaAdapter.SiswaViewHolder>() {

    private val listData = ArrayList<ResultsSiswa>()
    private lateinit var onItemClickCallback: OnItemClickCallback
    private lateinit var onEditClickCallback: OnEditClickCallback

    fun setOnClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setOnEditClickCallback(onEditClickCallback: OnEditClickCallback) {
        this.onEditClickCallback = onEditClickCallback
    }

    fun setData(siswas: ArrayList<ResultsSiswa>) {
        listData.clear()
        listData.addAll(siswas)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiswaViewHolder {
        val view = ItemDataSiswaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SiswaViewHolder((view))
    }

    override fun onBindViewHolder(holder: SiswaViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    inner class SiswaViewHolder(private val binding: ItemDataSiswaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(siswa: ResultsSiswa) {
            binding.root.setOnClickListener {
                onItemClickCallback.onItemClicked(siswa)
            }

            binding.lihatDetail.setOnClickListener {
                onItemClickCallback.onItemClicked(siswa)
            }

            binding.edit.setOnClickListener {
                onEditClickCallback.onEditClicked(siswa)
            }

            binding.apply {
                tvNameSiswa.text = siswa.nama_siswa
            }
        }

    }


    interface OnItemClickCallback {
        fun onItemClicked(data: ResultsSiswa)
    }

    interface OnEditClickCallback{
        fun onEditClicked(data: ResultsSiswa)
    }
}