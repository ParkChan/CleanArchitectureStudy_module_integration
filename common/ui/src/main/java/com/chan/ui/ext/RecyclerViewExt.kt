package com.chan.ui.ext

import android.annotation.SuppressLint
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chan.ui.adapter.BaseAdapter

@SuppressLint("NotifyDataSetChanged")
@BindingAdapter("bind_recyclerView_replaceItem")
fun replaceItem(recyclerView: RecyclerView, items: List<Any>?) {
    if (items == null) return

    @Suppress("UNCHECKED_CAST")
    (recyclerView.adapter as BaseAdapter<Any>).run {
        replaceItems(items)
        notifyDataSetChanged()
    }
}