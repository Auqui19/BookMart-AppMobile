package com.auqui.bookmart.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.auqui.bookmart.Helper.ChangeNumberItemsListener
import com.auqui.bookmart.Helper.ManagmentCart
import com.auqui.bookmart.Model.ItemsModel
import com.auqui.bookmart.databinding.ViewholderCartBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions

class CartAdapter (private val listItemSelected: ArrayList<ItemsModel>,
                   context:
                   Context, var changeNumberItemsListener: ChangeNumberItemsListener ?= null)
                    : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    class ViewHolder(val binding: ViewholderCartBinding): RecyclerView.ViewHolder(binding.root) {


    }

    private val managementCart = ManagmentCart(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        val binding = ViewholderCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
        val item = listItemSelected[position]
        holder.binding.bookTitle.text = item.title
        holder.binding.costoItem.text = "S/ ${item.price}"
        holder.binding.preciototalItem.text = "S/ ${Math.round(item.numberInCart * item.price)}"
        holder.binding.numbreItemTxt.text = item.numberInCart.toString()

        Glide.with(holder.itemView.context)
            .load(item.picUrl[0])
            .apply(RequestOptions().transform(CenterCrop()))
            .into(holder.binding.picBook)

        holder.binding.plusCartBtn.setOnClickListener {
            managementCart.plusItem(listItemSelected, position, object : ChangeNumberItemsListener{
                override fun onChanged() {
                    notifyDataSetChanged()
                    changeNumberItemsListener?.onChanged()
                }
            })
        }

        holder.binding.minusCartBtn.setOnClickListener {
            managementCart.minusItem(listItemSelected, position, object : ChangeNumberItemsListener{
                override fun onChanged() {
                    notifyDataSetChanged()
                    changeNumberItemsListener?.onChanged()
                }
            })
        }
    }

    override fun getItemCount(): Int = listItemSelected.size
}