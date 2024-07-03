package com.auqui.bookmart.activity

import android.content.Intent
import android.os.Bundle
import com.auqui.bookmart.databinding.ActivityIntroBinding

class IntroActivity : BaseActivity() {

    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btEmpezemos.setOnClickListener {
            startActivity(Intent(this@IntroActivity, LoginActivity::class.java))
            finish()
        }

        binding.btResgistro.setOnClickListener {
            startActivity(Intent(this@IntroActivity, RegistarActivity::class.java))
            finish()
        }
    }
}