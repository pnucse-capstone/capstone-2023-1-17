package com.example.frontend

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import com.unity3d.player.UnityPlayer
import com.unity3d.player.UnityPlayerActivity

class OpenUnityActivity : UnityPlayerActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_unity)

        val message = intent.getStringExtra("message")
        if (message != null) {
            val intent = Intent(this, UnityPlayerActivity::class.java)
            startActivity(intent)
            // UnityPlayer.UnitySendMessage("AR Session Origin", "ReceiveMessage", message)

            Handler(
                Looper.getMainLooper()).postDelayed({
                    Log.d("보내기전", message)
                    UnityPlayer.UnitySendMessage("AR Session Origin", "ReceiveMessage", message)
                    Log.d("보내기후", "보내기 후")
            }, 5000)
        }
    }
}
