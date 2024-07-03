package com.auqui.bookmart.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.auqui.bookmart.databinding.ActivityRegistarBinding
import com.google.firebase.auth.FirebaseAuth

class RegistarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistarBinding
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.regresar.setOnClickListener {
            startActivity(Intent(this@RegistarActivity, LoginActivity::class.java))
            finish()
        }

        binding.btnBack.setOnClickListener {
            startActivity(Intent(this@RegistarActivity, IntroActivity::class.java))
            finish()
        }

        binding.btnRegister.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this@RegistarActivity,
                    "Debes ingresar un email y una contraseña",
                    Toast.LENGTH_SHORT
                ).show()
                binding.progressBar.visibility = View.GONE
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    binding.progressBar.visibility = View.GONE
                    if (task.isSuccessful) {
                        Toast.makeText(
                            baseContext,
                            "Usuario registrado correctamente",
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(this@RegistarActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            baseContext,
                            "Error al registrar usuario",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}