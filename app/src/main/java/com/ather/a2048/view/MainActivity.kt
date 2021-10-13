package com.ather.a2048.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.ather.a2048.R
import com.ather.a2048.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

    }

    fun onResetClicked(v: View) {

    }

    override fun onDestroy() {
        super.onDestroy()
        binding?.let {
            it.unbind()
            binding = null;
        }
    }
}