package com.example.onlineshoppingapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore.Images
import android.renderscript.ScriptGroup.Binding
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.onlineshoppingapp.Model.SliderModel
import com.example.onlineshoppingapp.R
import com.example.onlineshoppingapp.SliderAdapter
import com.example.onlineshoppingapp.ViewModel.MainViewModel
import com.example.onlineshoppingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel=MainViewModel()
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBanner()



    }
    private fun initBanner(){
        binding.progressBarBanner.visibility=View.VISIBLE
        viewModel.banners.observe(this, Observer { items->
            banners(items)
            binding.progressBarBanner.visibility=View.GONE
        })
        viewModel.loadBanners()
    }
    private fun banners(images:List<SliderModel>){
        binding.viewpagerSlider.adapter=SliderAdapter(images,binding.viewpagerSlider)
        binding.viewpagerSlider.clipToPadding=false
        binding.viewpagerSlider.clipChildren=false
        binding.viewpagerSlider.offscreenPageLimit=3
        binding.viewpagerSlider.getChildAt(0).overScrollMode=RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer=CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
        }
        binding.viewpagerSlider.setPageTransformer(compositePageTransformer)
        if (images.size>1){
            binding.DotIndicator.visibility=View.VISIBLE
            binding.DotIndicator.attachTo(binding.viewpagerSlider)
        }
    }
}


