package com.goldmansachs.assignment.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.goldmansachs.assignment.R
import com.goldmansachs.assignment.databinding.MainViewholderBinding
import com.goldmansachs.assignment.model.apiservice.ApodEntity

class MainAdapter(private val onItemClick: (ApodEntity) -> Unit) :
    ListAdapter<ApodEntity, MainViewHolder>(MainAdapterComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder =
        MainViewHolder(
            MainViewholderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onItemClick
        )

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

}

class MainViewHolder(
    private val binding: MainViewholderBinding,
    private val onItemClick: (ApodEntity) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(entity: ApodEntity) {
        binding.apply {
            root.setOnClickListener { onItemClick(entity) }
            Glide.with(itemView)
                .load(entity.hdurl)
                .placeholder(R.drawable.ic_error)
                .into(binding.apodImage)
            binding.apodTitle.text = entity.title ?: "Unknown"
            binding.apodDate.text = entity.date ?: "Unknown"
        }
    }

}

class MainAdapterComparator : DiffUtil.ItemCallback<ApodEntity>() {
    override fun areItemsTheSame(oldItem: ApodEntity, newItem: ApodEntity): Boolean =
        oldItem.url == newItem.url

    override fun areContentsTheSame(oldItem: ApodEntity, newItem: ApodEntity): Boolean =
        oldItem == newItem

}