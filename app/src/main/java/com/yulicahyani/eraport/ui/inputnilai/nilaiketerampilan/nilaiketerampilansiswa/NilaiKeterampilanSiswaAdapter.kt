package com.yulicahyani.eraport.ui.inputnilai.nilaiketerampilan.nilaiketerampilansiswa

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yulicahyani.eraport.data.source.remote.response.ResultsListNilaiKeterampilan
import com.yulicahyani.eraport.databinding.ItemKeterampilanSiswaBinding
import java.lang.StringBuilder

class NilaiKeterampilanSiswaAdapter : RecyclerView.Adapter<NilaiKeterampilanSiswaAdapter.KeterampilanSiswaViewHolder>() {

    private val listData = ArrayList<ResultsListNilaiKeterampilan>()
    private lateinit var onItemClickCallback: NilaiKeterampilanSiswaAdapter.OnItemClickCallback

    fun setOnClickCallback(onItemClickCallback: NilaiKeterampilanSiswaAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(nilai: ArrayList<ResultsListNilaiKeterampilan>) {
        listData.clear()
        listData.addAll(nilai)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeterampilanSiswaViewHolder {
        val view =
            ItemKeterampilanSiswaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return KeterampilanSiswaViewHolder((view))
    }

    override fun onBindViewHolder(holder: KeterampilanSiswaViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    inner class KeterampilanSiswaViewHolder (private val binding: ItemKeterampilanSiswaBinding)
        :RecyclerView.ViewHolder (binding.root) {

            fun bind(nilai: ResultsListNilaiKeterampilan) {

                binding.root.setOnClickListener {
                    onItemClickCallback.onItemClicked(nilai)
                }
                val kodeKd = StringBuilder()
                binding.apply {
                    tvNameKd.text = kodeKd.append("KD ").append(nilai.kode_kd)
                    tvNameKeterampilan.text = nilai.nama_keterampilan
                    tvNilai.text = nilai.nilai_kt
                }

            }

    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ResultsListNilaiKeterampilan)
    }
}