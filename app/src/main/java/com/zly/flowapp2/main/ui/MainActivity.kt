package com.zly.flowapp2.main.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zly.flowapp2.R
import com.zly.flowapp2.util.ext.addFragmentToActivity
import com.zly.flowapp2.databinding.ActivityMainBinding
import com.zly.flowapp2.main.ui.fragment.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        addFragmentToActivity(
            MainFragment.newInstance(),
            R.id.main_container,
            false,
            MainFragment.tag()
        )
    }
}
