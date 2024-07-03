package com.auqui.bookmart.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.auqui.bookmart.R
import com.auqui.bookmart.databinding.ViewholderCatBinding
import com.auqui.bookmart.databinding.ViewholderColorBinding
import com.bumptech.glide.Glide

class CatAdapter(val items: MutableList<String>) :
    RecyclerView.Adapter<CatAdapter.Viewholder>() {

    private var selectedPosition = -1
    private var lastSelectedPosition = -1
    private lateinit var context: Context

    class Viewholder(val binding: ViewholderCatBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        context = parent.context
        val binding = ViewholderCatBinding.inflate(LayoutInflater.from(context), parent, false)
        return Viewholder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: Viewholder, position: Int) {

        holder.binding.catTxt.text = items[position]

        holder.binding.root.setOnClickListener {
            lastSelectedPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
        }

        if (selectedPosition == position) {
            holder.binding.catLayout.setBackgroundResource(R.drawable.bg_gray_selectd)
            holder.binding.catTxt.setTextColor(context.resources.getColor(R.color.azul))
        } else {
            holder.binding.catLayout.setBackgroundResource(R.drawable.bg_gray)
            holder.binding.catTxt.setTextColor(context.resources.getColor(R.color.black))
        }
    }
}