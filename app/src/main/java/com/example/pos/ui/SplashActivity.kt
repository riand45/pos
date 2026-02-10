package com.example.pos.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.pos.PosApplication
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.pos.R.layout.activity_splash)

        lifecycleScope.launch {
            // Add a small delay for better UX (optional, but good for branding)
            delay(1000)

            val app = application as PosApplication
            val session = try {
                app.supabase.auth.awaitInitialization()
                app.supabase.auth.currentSessionOrNull()
            } catch (e: Exception) {
                null
            }

            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            if (session != null) {
                // User is logged in
                intent.putExtra("LOGGED_IN", true)
            }
            startActivity(intent)
            finish()
        }
    }
}
