package com.auqui.bookmart.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.auqui.bookmart.Model.ItemsModel
import com.auqui.bookmart.R
import com.auqui.bookmart.activity.DetailActivity
import com.auqui.bookmart.databinding.ViewHolderBookBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions

class BookAdapter(val items: MutableList<ItemsModel>) :
    RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    private var context: Context? = null

    class ViewHolder(val binding: ViewHolderBookBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookAdapter.ViewHolder {
        context = parent.context
        val binding = ViewHolderBookBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookAdapter.ViewHolder, position: Int) {
        holder.binding.titleText.text = items[position].title
        holder.binding.priceText.text = "S/ " + items[position].price.toString()
        holder.binding.ratingText.text = items[position].rating.toString()

        val requestOptions = RequestOptions().transform(CenterCrop())
        Glide.with(holder.itemView.context)
            .load(items[position].picUrl[0])
            .apply(requestOptions)
            .into(holder.binding.bookImg)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("object", items[position])
            holder.itemView.context.startActivity(intent)
        }

        var like = false
        holder.binding.btnlike.setOnClickListener {
            like = likeAnimacion(holder.binding.btnlike, R.raw.apple_event, like)
        }

    }

    override fun getItemCount(): Int = items.size

    private  fun likeAnimacion(imageView: LottieAnimationView, animation: Int, like: Boolean): Boolean {
        if (!like) {
            imageView.setAnimation(animation)
            imageView.scaleX = 2.5f
            imageView.scaleY = 2.5f
            imageView.playAnimation()
        } else {
            imageView.scaleX = 1.0f
            imageView.scaleY = 1.0f
            imageView.setImageResource(R.drawable.twitter_like)
        }
        return  !like
    }

}