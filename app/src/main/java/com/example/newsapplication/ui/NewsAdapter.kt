package com.example.newsapplication.ui

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.data.Articles
import com.example.newsapplication.databinding.ItemNewsEmptyBinding
import com.example.newsapplication.databinding.ItemNewsListBinding
import com.example.newsapplication.ui.base.BaseViewHolder
import com.squareup.picasso.Picasso

class NewsAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    private var context: Context? = null
    private var listener: OnItemClickListener? = null
    private var mList:List<Articles>?=null

    fun setOnItemListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun setList(mList:List<Articles>)
    {
        this.mList = mList
        notifyDataSetChanged()
    }

    companion object {
        private const val VIEW_TYPE_EMPTY = 0
        private const val VIEW_TYPE_NORMAL = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        context = parent.context
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val simpleViewItemBinding: ItemNewsListBinding =
                    ItemNewsListBinding.inflate(
                                LayoutInflater.from(parent.context), parent, false)
                SimpleViewHolder(simpleViewItemBinding)
            }
            else -> {
                val emptyViewBinding: ItemNewsEmptyBinding =
                    ItemNewsEmptyBinding.inflate(
                                LayoutInflater.from(parent.context), parent, false)
                EmptyViewHolder(emptyViewBinding)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (mList != null && mList!!.isNotEmpty()) {
            mList!!.size
        } else {
            1
        }
    }

    override fun getItemViewType(position: Int) = if (mList != null && mList!!.isNotEmpty()) {
        VIEW_TYPE_NORMAL
    } else {
        VIEW_TYPE_EMPTY
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) = holder.onBind(position)

    interface OnItemClickListener {
        fun onClickItem(pos: Int)
    }

    inner class SimpleViewHolder(private val mBinding: ItemNewsListBinding) :
            BaseViewHolder(mBinding.root), NewsItemViewModel.NewsItemListener {

        private lateinit var simpleViewModel: NewsItemViewModel
        private var pos:Int = 0
        override fun onBind(position: Int) {
            val lists = mList!![position]
            if(mList!![position].urlToImage!=null) {
                val uri: String = mList!![position].urlToImage!!
                Picasso.get()
                    .load(Uri.parse(uri))
                    .into(mBinding.ivNews);
            }
            simpleViewModel = NewsItemViewModel(lists, this)
            mBinding.viewModel = simpleViewModel
            pos = position

            mBinding.executePendingBindings()
        }

        override fun onClickItem() {
            listener?.onClickItem(pos)
        }
    }

    inner class EmptyViewHolder(private val mBinding: ItemNewsEmptyBinding) :
            BaseViewHolder(mBinding.root) {

        override fun onBind(position: Int) {
            val emptyViewModel = NewsEmptyItemViewModel()
            mBinding.viewModel = emptyViewModel

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings()
        }
    }
}