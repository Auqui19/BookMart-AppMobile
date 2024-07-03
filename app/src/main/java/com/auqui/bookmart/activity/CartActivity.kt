package com.auqui.bookmart.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.auqui.bookmart.Adapter.CartAdapter
import com.auqui.bookmart.Helper.ChangeNumberItemsListener
import com.auqui.bookmart.Helper.ManagmentCart
import com.auqui.bookmart.R
import com.auqui.bookmart.databinding.ActivityCartBinding

class CartActivity : BaseActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var managmentCart: ManagmentCart
    private var tax: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart = ManagmentCart(this)
        setVariable()
        initCartList()
        calcularCart()

        binding.btnPago.setOnClickListener {
            realizarPago()
        }
    }

    private fun initCartList() {
        binding.viewCart.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.viewCart.adapter =
            CartAdapter(managmentCart.getListCart(), this, object : ChangeNumberItemsListener {
                override fun onChanged() {
                    calcularCart()
                }
            })

        with(binding) {
            emptyTxt.visibility =
                if (managmentCart.getListCart().isEmpty()) View.VISIBLE else View.GONE
            scrollView.visibility =
                if (managmentCart.getListCart().isEmpty()) View.GONE else View.VISIBLE
        }
    }

    private fun calcularCart() {
        val envio = 10.0
        val descuento = 0.1
        tax = Math.round((managmentCart.getTotalFee() * descuento) * 100) / 100.0
        val total = Math.round((managmentCart.getTotalFee() - tax + envio) * 100) / 100
        val itemTotal = Math.round(managmentCart.getTotalFee() * 100) / 100

        with(binding) {
            totalBookTxt.text = "S/ $itemTotal"
            envioTxt.text = "S/ $envio"
            descuentoTxt.text = "S/ $tax"
            totalTxt.text = "S/ $total"
        }
    }

    private fun setVariable() {
        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    private fun realizarPago() {
        // Eliminar los artículos del carrito
        managmentCart.clearCart()

        // Actualizar la vista
        initCartList()
        calcularCart()

        // Mostrar notificación
        Toast.makeText(this, "Se realizó el pago correctamente", Toast.LENGTH_LONG).show()
    }
}