package com.auqui.bookmart.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.auqui.bookmart.R
import com.auqui.bookmart.databinding.ActivityMainBinding
import com.auqui.bookmart.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using View Binding
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the delivery time dynamically
        binding.deliveryTime.text = "Delivery Time 32 min"

        binding.btnContact.setOnClickListener {
            Toast.makeText(this, "Contacto en progreso", Toast.LENGTH_SHORT).show()
        }

        binding.backBtn.setOnClickListener {
            startActivity(Intent(this@MapActivity, MainActivity::class.java))
            finish()
        }
    }
}