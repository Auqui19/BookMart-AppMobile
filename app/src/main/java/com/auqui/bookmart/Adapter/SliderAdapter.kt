package com.auqui.bookmart.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.auqui.bookmart.Model.SliderModel
import com.auqui.bookmart.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.request.RequestOptions

class SliderAdapter(
    private var slidersItems: List<SliderModel>,
    private val viewPager2: ViewPager2
) : RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    private lateinit var context: Context
    private val runnable = Runnable {
        slidersItems = slidersItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SliderViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.slider_item_container, parent, false)
        return SliderViewHolder(view)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.setImage(slidersItems[position], context)
        if (position == slidersItems.lastIndex-1){
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int = slidersItems.size


    class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageSlide)
        fun setImage(sliderItems: SliderModel, context: Context) {
            val requestOptions = RequestOptions().transforms(CenterInside())
            Glide.with(context).load(sliderItems.url).apply(requestOptions).into(imageView)
        }
    }
}