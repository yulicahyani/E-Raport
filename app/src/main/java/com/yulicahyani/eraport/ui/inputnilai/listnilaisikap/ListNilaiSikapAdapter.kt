package com.yulicahyani.eraport.ui.inputnilai.listnilaisikap

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yulicahyani.eraport.data.source.remote.response.ResultsListNilaiSikap
import com.yulicahyani.eraport.databinding.ItemListNilaiSikapBinding

class ListNilaiSikapAdapter : RecyclerView.Adapter<ListNilaiSikapAdapter.NilaiSikapViewHolder>() {

    private val listData = ArrayList<ResultsListNilaiSikap>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setData(nilaisikap: ArrayList<ResultsListNilaiSikap>) {
        listData.clear()
        listData.addAll(nilaisikap)
        notifyDataSetChanged()
    }

    fun setOnClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NilaiSikapViewHolder {
        val view =
            ItemListNilaiSikapBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NilaiSikapViewHolder((view))
    }

    override fun onBindViewHolder(holder: NilaiSikapViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size
    @SuppressLint("SetTextI18n")
    inner class NilaiSikapViewHolder(private val binding: ItemListNilaiSikapBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(nilaisikap: ResultsListNilaiSikap) {
            binding.root.setOnClickListener {
                onItemClickCallback.onItemClicked(nilaisikap)
            }

            binding.apply {
                tvNameSiswa.text = nilaisikap.nama_siswa
                tvDescNilai.text = nilaisikap.deskripsi
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ResultsListNilaiSikap)
    }

}