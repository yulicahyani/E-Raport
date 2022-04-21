package com.yulicahyani.eraport.ui.datautama.datauser

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yulicahyani.eraport.data.source.remote.response.ResultsAllUser
import com.yulicahyani.eraport.databinding.ItemDataUserBinding

class DataUserAdapter : RecyclerView.Adapter<DataUserAdapter.UserViewHolder>() {

    private val listData = ArrayList<ResultsAllUser>()
    private lateinit var onItemClickCallback: OnItemClickCallback
    private lateinit var onEditClickCallback: OnEditClickCallback
    private lateinit var onDeleteClickCallback: OnDeleteClickCallback

    fun setOnClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setOnEditClickCallback(onEditClickCallback: OnEditClickCallback) {
        this.onEditClickCallback = onEditClickCallback
    }
    fun setOnDeleteClickCallback(onDeleteClickCallback: OnDeleteClickCallback) {
        this.onDeleteClickCallback = onDeleteClickCallback
    }

    fun setData(user: ArrayList<ResultsAllUser>) {
        listData.clear()
        listData.addAll(user)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemDataUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder((view))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    inner  class UserViewHolder (private val binding : ItemDataUserBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(user: ResultsAllUser){
            binding.root.setOnClickListener {
                onItemClickCallback.onItemClicked(user)
            }
            binding.lihatDetail.setOnClickListener {
                onItemClickCallback.onItemClicked(user)
            }

            binding.edit.setOnClickListener {
                onEditClickCallback.onEditClicked(user)
            }

            binding.hapus.setOnClickListener {
                onDeleteClickCallback.onDeleteClicked(user)
            }

            binding.apply {
                tvNameUser.text = user.firstname + ' ' + user.lastname
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ResultsAllUser)
    }
    interface OnEditClickCallback{
        fun onEditClicked(data: ResultsAllUser)
    }

    interface OnDeleteClickCallback{
        fun onDeleteClicked(data: ResultsAllUser)
    }
}