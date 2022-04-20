package com.yulicahyani.eraport.ui.raport

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yulicahyani.eraport.data.source.remote.response.ResultsNilaiFinalPengetahuan
import com.yulicahyani.eraport.databinding.ItemNilaiPengetahuanBinding

class NilaiFinalPengetahuanAdapter : RecyclerView.Adapter<NilaiFinalPengetahuanAdapter.FinalPengetahuanViewHolder>() {

    private val listData = ArrayList<ResultsNilaiFinalPengetahuan>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(nilai: ArrayList<ResultsNilaiFinalPengetahuan>) {
        listData.clear()
        listData.addAll(nilai)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinalPengetahuanViewHolder {
        val view =
            ItemNilaiPengetahuanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FinalPengetahuanViewHolder((view))
    }

    override fun onBindViewHolder(holder: FinalPengetahuanViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size


    inner class FinalPengetahuanViewHolder(private val binding: ItemNilaiPengetahuanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(nilai: ResultsNilaiFinalPengetahuan) {
            binding.root.setOnClickListener {
                onItemClickCallback.onItemClicked(nilai)
            }

            binding.apply {
                namaMapel.text = nilai.nama_mapel
                nilaiAkhir.text = nilai.nilai_akhir
                predikat.text = nilai.predikat
                deskripsi.text = nilai.deskripsi
            }
        }

    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ResultsNilaiFinalPengetahuan)
    }


}