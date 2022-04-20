package com.yulicahyani.eraport.ui.raport

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yulicahyani.eraport.data.source.remote.response.ResultsNilaiFinalKeterampilan
import com.yulicahyani.eraport.databinding.ItemNilaiKeterampilanBinding

class NilaiFinalKeterampilanAdapter : RecyclerView.Adapter<NilaiFinalKeterampilanAdapter.FinalKeterampilanViewHolder>() {

    private val listData = ArrayList<ResultsNilaiFinalKeterampilan>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(nilai: ArrayList<ResultsNilaiFinalKeterampilan>) {
        listData.clear()
        listData.addAll(nilai)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinalKeterampilanViewHolder {
        val view =
            ItemNilaiKeterampilanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FinalKeterampilanViewHolder((view))
    }

    override fun onBindViewHolder(holder: FinalKeterampilanViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size


    inner class FinalKeterampilanViewHolder(private val binding: ItemNilaiKeterampilanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(nilai: ResultsNilaiFinalKeterampilan) {
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
        fun onItemClicked(data: ResultsNilaiFinalKeterampilan)
    }


}