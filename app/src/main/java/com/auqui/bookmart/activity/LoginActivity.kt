package com.auqui.bookmart.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.auqui.bookmart.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onStart() {
        super.onStart()
        // Verifica si el usuario está firmado (no nulo) y actualiza la UI en consecuencia.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registar.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegistarActivity::class.java))
            finish()
        }

        binding.btnLogin.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@LoginActivity, "Debe ingresar un email y una contraseña", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    binding.progressBar.visibility = View.GONE
                    if (task.isSuccessful) {
                        Toast.makeText(baseContext, "Sesión iniciada correctamente", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(baseContext, "Error al iniciar sesión", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        binding.btnBack.setOnClickListener {
            startActivity(Intent(this@LoginActivity, IntroActivity::class.java))
            finish()
        }
    }
}