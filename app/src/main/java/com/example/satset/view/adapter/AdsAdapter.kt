package com.example.satset.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.satset.data.remote.response.ads.DataItem
import com.example.satset.databinding.ItemAdsBinding

class AdsAdapter(private val adsList: List<DataItem?>) :
    RecyclerView.Adapter<AdsAdapter.AdsViewHolder>() {

    inner class AdsViewHolder(private val binding: ItemAdsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(adsItem: DataItem) {

            Glide.with(binding.root.context)
                .load(adsItem.adsImageUrl)
                .into(binding.ivAds)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdsViewHolder {
        val binding = ItemAdsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AdsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdsViewHolder, position: Int) {
        adsList[position]?.let { holder.bind(it) }
    }

    override fun getItemCount() = adsList.size
}
