package com.auqui.bookmart.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.auqui.bookmart.Adapter.CatAdapter
import com.auqui.bookmart.Adapter.CategoryAdapter
import com.auqui.bookmart.Adapter.ColorAdapter
import com.auqui.bookmart.Adapter.SliderAdapter
import com.auqui.bookmart.Helper.ManagmentCart
import com.auqui.bookmart.Model.ItemsModel
import com.auqui.bookmart.Model.SliderModel
import com.auqui.bookmart.R
import com.auqui.bookmart.databinding.ActivityDetailBinding

class DetailActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var item: ItemsModel
    private var numberOrder = 1
    private lateinit var managmentCart: ManagmentCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart = ManagmentCart(this)

        getBundle()
        banners()
        initList()

        var like = false
        binding.favBtn.setOnClickListener {
            like = likeAnimacion(binding.favBtn, R.raw.apple_event, like)
        }
    }

    private fun initList() {
        val catList = ArrayList<String>()
        for (size in item.genre) {
            catList.add(size.toString())
        }

        binding.catList.adapter = CatAdapter(catList)
        binding.catList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val colorList = ArrayList<String>()
        for (imageUrl in item.picUrl) {
            colorList.add(imageUrl)
        }

        binding.detalleList.adapter = ColorAdapter(colorList)
        binding.detalleList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun banners() {
        val sliderItems = ArrayList<SliderModel>()
        for (imageUrl in item.picUrl) {
            sliderItems.add(SliderModel(imageUrl))
        }

        binding.slider.adapter = SliderAdapter(sliderItems, binding.slider)
        binding.slider.clipToPadding = true
        binding.slider.clipChildren = true
        binding.slider.offscreenPageLimit = 1

        if (sliderItems.size > 1) {
            binding.dotIndicator.visibility = View.VISIBLE
            binding.dotIndicator.attachTo(binding.slider)
        }
    }

    private fun getBundle() {
        item = intent.getParcelableExtra("object")!!

        binding.titleTxt.text = item.title
        binding.autorTxt.text = item.author
        binding.descriptionTxt.text = item.description
        binding.priceTxt.text = "S/ " + item.price.toString()
        binding.ratingTxt.text = item.rating.toString()
        binding.addCarBtn.setOnClickListener {
            item.numberInCart = numberOrder
            managmentCart.insertFood(item)
        }
        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.cartBtn.setOnClickListener {
            startActivity(Intent(this@DetailActivity, CartActivity::class.java))
        }
    }

    private fun likeAnimacion(
        imageView: LottieAnimationView,
        animation: Int,
        like: Boolean
    ): Boolean {
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
        return !like
    }
}