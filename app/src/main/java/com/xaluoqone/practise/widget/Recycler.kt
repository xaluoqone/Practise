package com.xaluoqone.practise.widget

import android.annotation.SuppressLint
import android.app.Activity
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView

fun Activity.recycler(scope: RecyclerView.() -> Unit): RecyclerView {
    return RecyclerView(this).apply(scope)
}

context(RecyclerView)
fun <T> adapter(
    data: List<T>,
    onCreateItem: LinearLayoutCompat.() -> Unit,
    onBindData: LinearLayoutCompat.(T) -> Unit
): RecyclerView.Adapter<CommonHolder<T>> {
    return object : RecyclerView.Adapter<CommonHolder<T>>(), DataAdapter<T> {
        private var list = data

        @SuppressLint("NotifyDataSetChanged")
        override fun setData(data: List<T>) {
            list = data
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonHolder<T> {
            return viewHolder(onCreateItem, onBindData)
        }

        override fun getItemCount() = list.size

        override fun onBindViewHolder(holder: CommonHolder<T>, position: Int) {
            holder.bind(list[position])
        }
    }
}

interface DataAdapter<T> {
    fun setData(data: List<T>)
}

fun <T> RecyclerView.viewHolder(
    onCreateItem: LinearLayoutCompat.() -> Unit,
    onBindData: LinearLayoutCompat.(T) -> Unit
): CommonHolder<T> {
    val itemView = LinearLayoutCompat(context)
    itemView.onCreateItem()
    return object : CommonHolder<T>(itemView) {
        override fun bind(data: T) {
            itemView.onBindData(data)
        }
    }
}

abstract class CommonHolder<T>(itemView: ViewGroup) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(data: T)
}