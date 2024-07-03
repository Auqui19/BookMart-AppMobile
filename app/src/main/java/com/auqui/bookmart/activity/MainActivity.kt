package com.auqui.bookmart.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.auqui.bookmart.Adapter.BookAdapter
import com.auqui.bookmart.Adapter.CategoryAdapter
import com.auqui.bookmart.Model.SliderModel
import com.auqui.bookmart.Adapter.SliderAdapter
import com.auqui.bookmart.ViewModel.MainViewModel
import com.auqui.bookmart.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : BaseActivity() {
    private val viewModel = MainViewModel()
    private lateinit var binding: ActivityMainBinding

    // Verificar si el usuario está autenticado
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Verificar si el usuario está autenticado
        if (auth.currentUser == null) {
            navigateToLogin()
            return
        }

        binding.txtCorreo.text = auth.currentUser!!.email ?: ""
        initBanner()
        initCategory()
        initBook()
        initBotonesMenu()
    }

    // Botones de menú
    private fun initBotonesMenu() {
        binding.cartBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, CartActivity::class.java))
        }

        binding.inicioBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, MainActivity::class.java))
        }

        binding.mapsBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, MapActivity::class.java))
        }

        binding.salirBtn.setOnClickListener {
            auth.signOut()
            navigateToLogin()
        }

    }

    private fun initBanner() {
        binding.progressBarBanner.visibility = View.VISIBLE
        viewModel.banners.observe(this, Observer { items ->
            banners(items)
            binding.progressBarBanner.visibility = View.GONE
        })
        viewModel.loadBanners()
    }

    // Función para cargar banners
    private fun banners(images: List<SliderModel>) {
        val adapter = SliderAdapter(images, binding.viewpageSlider)
        binding.viewpageSlider.adapter = adapter
        binding.viewpageSlider.clipToPadding = false
        binding.viewpageSlider.clipChildren = false
        binding.viewpageSlider.offscreenPageLimit = 3
        binding.viewpageSlider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
        }
        binding.viewpageSlider.setPageTransformer(compositePageTransformer)
        if (images.size > 1) {
            binding.dotIndicator.visibility = View.VISIBLE
            binding.dotIndicator.attachTo(binding.viewpageSlider)
        } else {
            binding.dotIndicator.visibility = View.GONE
        }
    }

    // Función para cargar categorías
    private fun initCategory() {
        binding.progressCategory.visibility = View.VISIBLE
        viewModel.category.observe(this, Observer {
            binding.viewCategory.layoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayoutManager.HORIZONTAL, false
            )
            binding.viewCategory.adapter = CategoryAdapter(it)
            binding.progressCategory.visibility = View.GONE
        })
        viewModel.loadCategory()
    }

    // Función para cargar libros
    private fun initBook() {
        binding.progressBarBook.visibility = View.VISIBLE
        viewModel.book.observe(this, Observer {
            binding.viewBook.layoutManager = GridLayoutManager(this@MainActivity, 2)
            binding.viewBook.adapter = BookAdapter(it)
            binding.progressBarBook.visibility = View.GONE
        })
        viewModel.loadBooks()
    }

    // Función para redireccionar al login
    private fun navigateToLogin() {
        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        finish()
    }
}