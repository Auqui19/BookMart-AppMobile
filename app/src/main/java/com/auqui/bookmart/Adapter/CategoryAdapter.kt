package com.auqui.bookmart.Adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.auqui.bookmart.Model.CategoryModel
import com.auqui.bookmart.R
import com.auqui.bookmart.databinding.ActivityMainBinding
import com.auqui.bookmart.databinding.ViewHolderCategoryBinding
import com.bumptech.glide.Glide

class CategoryAdapter(val items: MutableList<CategoryModel>) :
    RecyclerView.Adapter<CategoryAdapter.Viewholder>() {

    private var selectedPosition = -1
    private var lastSelectedPosition = -1
    private lateinit var context: Context

    class Viewholder(val binding: ViewHolderCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        context = parent.context
        val binding = ViewHolderCategoryBinding.inflate(LayoutInflater.from(context), parent, false)
        return Viewholder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val item = items[position]
        holder.binding.titleBook.text = item.title
        Glide.with(holder.itemView.context)
            .load(item.picUrl)
            .into(holder.binding.book)
        holder.binding.root.setOnClickListener {
            lastSelectedPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
        }
        holder.binding.titleBook.setTextColor(context.resources.getColor(R.color.white))
        if (selectedPosition == position) {
            holder.binding.book.setBackgroundResource(0)
            holder.binding.mainLayout.setBackgroundResource(R.drawable.bg_azul)
            ImageViewCompat.setImageTintList(
                holder.binding.book,
                ColorStateList.valueOf(context.getColor(R.color.white))
            )
            holder.binding.titleBook.visibility = View.VISIBLE
        } else {
            holder.binding.book.setBackgroundResource(R.drawable.bg_gray)
            holder.binding.mainLayout.setBackgroundResource(0)
            ImageViewCompat.setImageTintList(
                holder.binding.book,
                ColorStateList.valueOf(context.getColor(R.color.black))
            )
            holder.binding.titleBook.visibility = View.GONE
        }
    }
}