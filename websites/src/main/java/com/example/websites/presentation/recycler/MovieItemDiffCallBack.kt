package com.example.websites.presentation.recycler

import androidx.recyclerview.widget.DiffUtil
import com.example.core_data.models.PasswordModel

object MovieItemDiffCallBack: DiffUtil.ItemCallback<PasswordModel>() {
    override fun areItemsTheSame(oldItem: PasswordModel, newItem: PasswordModel): Boolean {
        return oldItem == newItem
    }
    override fun areContentsTheSame(oldItem: PasswordModel, newItem: PasswordModel): Boolean {
        return oldItem == newItem
    }
}
