package com.example.websites.presentation.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.core_data.models.PasswordModel
import com.example.websites.databinding.ListItemBinding
import com.squareup.picasso.Picasso

class WebsitesListAdapter: ListAdapter<PasswordModel, MoviesHolder>(
    MovieItemDiffCallBack
) {
    var onItemClickListener: ((PasswordModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
        val binding = ListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MoviesHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: MoviesHolder, position: Int) {
        val websiteItem = getItem(position)
        val binding = viewHolder.binding
        binding.tvWebsiteUrl.text = websiteItem.websiteUrl
        binding.tvTitle.text = websiteItem.title
        Picasso.get().load(websiteItem.iconUrl).into(binding.iconWebsite)
        binding.movieListItem.setOnClickListener {
            onItemClickListener?.invoke(websiteItem)
        }
    }
}
