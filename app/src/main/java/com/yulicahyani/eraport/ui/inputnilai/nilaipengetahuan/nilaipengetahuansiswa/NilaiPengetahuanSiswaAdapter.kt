package com.yulicahyani.eraport.ui.inputnilai.nilaipengetahuan.nilaipengetahuansiswa

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yulicahyani.eraport.data.source.remote.response.ResultsListNilaiPengetahuan
import com.yulicahyani.eraport.databinding.ItemPengetahuanSiswaBinding


class NilaiPengetahuanSiswaAdapter: RecyclerView.Adapter<NilaiPengetahuanSiswaAdapter.PengetahuanSiswaViewHolder>() {

    private val listData = ArrayList<ResultsListNilaiPengetahuan>()
    private lateinit var onItemClickCallback: NilaiPengetahuanSiswaAdapter.OnItemClickCallback

    fun setOnClickCallback(onItemClickCallback: NilaiPengetahuanSiswaAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(nilai: ArrayList<ResultsListNilaiPengetahuan>) {
        listData.clear()
        listData.addAll(nilai)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PengetahuanSiswaViewHolder {
        val view = ItemPengetahuanSiswaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PengetahuanSiswaViewHolder((view))
    }

    override fun onBindViewHolder(holder: PengetahuanSiswaViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size


    inner class PengetahuanSiswaViewHolder (private val binding: ItemPengetahuanSiswaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(nilai: ResultsListNilaiPengetahuan) {
            binding.root.setOnClickListener {
                onItemClickCallback.onItemClicked(nilai)
            }

            binding.apply {
                tvNameTema.text = nilai.nama_tema
                tvNameKd.text = nilai.kode_kd
                tvNilai.text = nilai.nilai_kd
            }
        }

    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ResultsListNilaiPengetahuan)
    }


}