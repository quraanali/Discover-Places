package com.quraanali.discoverPlaces.ui.base

import android.os.Handler
import android.os.Looper
import android.text.BidiFormatter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
 * @param M is data model
 * @param V is a ViewHolder
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseAdapter<M, V : BaseViewHolder<M>> : RecyclerView.Adapter<V>() {

    var list = mutableListOf<M>()
    private var currentState: EnumState = EnumState.STATE_NORMAL
    private var rvHandler: Handler = Handler(Looper.getMainLooper())
    protected var onItemClickListener: OnItemClickListener<M>? = null
    private var onItemLongClickListener: OnItemLongClickListener<M>? = null

    fun isEmpty(): Boolean {
        return list.isEmpty()
    }

    fun deleteItem(position: Int) {
        rvHandler.post {
            if (position >= 0 && position < list.size) {
                list.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }

    fun deleteItem(item: M) {
        rvHandler.post {
            val position = list.indexOf(item)
            if (position < 0) return@post

            list.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun setItems(items: List<M>) {
        rvHandler.post {
            list.addAll(items)
            notifyItemRangeInserted(/*positionStart*/list.size,/*itemCount*/ items.size)
        }
    }

    fun loading(isLoading: Boolean) {
        rvHandler.post {
            currentState = if (isLoading) EnumState.STATE_LOADING else EnumState.STATE_NORMAL
            //            notifyItemChanged(list.size)
            notifyDataSetChanged()
        }
    }

    fun getAdapterState(): EnumState {
        return currentState
    }

    fun addItem(item: M) {
        rvHandler.post {
            list.add(item)
            notifyItemChanged(list.size - 1)
        }
    }

    fun addItem(item: M, position: Int) {
        rvHandler.post {
            list.add(position, item)
            notifyItemChanged(list.size - 1)
        }
    }

    fun updateItem(item: M) {
        rvHandler.post {
            val position = list.indexOf(item)
            if (position < 0) return@post

            list[position] = item
            notifyItemChanged(position)
        }
    }

    fun clear() {
        rvHandler.post {
            list.clear()
            notifyDataSetChanged()
        }
    }

    fun swapItems(dragFrom: Int, dragTo: Int) {
        Collections.swap(list, dragFrom, dragTo)
    }

    protected fun getItem(position: Int): M? {
        return if (position >= 0 && position < list.size) {
            list[position]
        } else {
            null
        }
    }

    override fun getItemCount(): Int {
        if (currentState == EnumState.STATE_LOADING) {
            return list.size + 1
        } else {
            return list.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position >= list.size)
            EnumState.STATE_LOADING.viewType()
        else EnumState.STATE_NORMAL.viewType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): V {
        return /* if (viewType == EnumState.STATE_LOADING.viewType()) {
            LoaderViewHolder(
                ViewItemLoaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            ) as (V)
        } else*/ getViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: V, position: Int) {
//        if (holder is LoaderViewHolder) {
//            return
//        }

        holder.bind(position, list[position])
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClicked(it, getItem(position), position)
        }

        holder.itemView.setOnLongClickListener { v: View? ->
            v?.let { onItemLongClickListener?.onItemLongClicked(it, getItem(position), position) }
            true
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener<M>?): BaseAdapter<M, V>? {
        this.onItemClickListener = onItemClickListener
        return this
    }

    fun setOnItemLongClickListener(onItemLongClickListener: OnItemLongClickListener<M>?): BaseAdapter<M, V>? {
        this.onItemLongClickListener = onItemLongClickListener
        return this
    }


    protected abstract fun getViewHolder(parent: ViewGroup, viewType: Int): V
    interface OnItemClickListener<M> {
        fun onItemClicked(view: View, item: M?, position: Int)
    }

    interface OnItemLongClickListener<M> {
        fun onItemLongClicked(view: View, item: M?, position: Int)
    }

//    class LoaderViewHolder(binding: ViewItemLoaderBinding) : BaseViewHolder<Nothing>(binding) {
//        override fun bind(position: Int, item: Nothing?) = bind<ViewItemLoaderBinding> {
//        }
//    }

    enum class EnumState {
        STATE_LOADING {
            override fun viewType() = -2
        },

        STATE_NORMAL {
            override fun viewType() = -1
        };

        abstract fun viewType(): Int
    }


    fun isLastItem(position: Int): Boolean {
        return (position == list.size - 1)
    }

    fun getTextDirection(value: String): String {
        return BidiFormatter.getInstance(Locale.getDefault()).unicodeWrap(value)
    }
}