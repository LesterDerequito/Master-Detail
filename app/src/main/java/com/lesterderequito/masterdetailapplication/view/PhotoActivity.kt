package com.lesterderequito.masterdetailapplication.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.lesterderequito.masterdetailapplication.DetailsActivity
import com.lesterderequito.masterdetailapplication.R
import com.lesterderequito.masterdetailapplication.adapter.PhotoAdapter
import com.lesterderequito.masterdetailapplication.databinding.ActivityPhotoBinding
import com.lesterderequito.masterdetailapplication.viewmodel.PhotoActivityViewModel
import kotlinx.coroutines.flow.collectLatest

class PhotoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhotoBinding
    private lateinit var photoAdapter: PhotoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        initSwipeRefreshLayout()
        refreshList()

        photoAdapter.onItemClick = { author: String, url: String, byte: ByteArray? ->
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("AUTHOR", author)
            intent.putExtra("URL", url)
            intent.putExtra("BYTE", byte)
            startActivity(intent)
        }
    }

    private fun initSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.light_green),
            ContextCompat.getColor(this, R.color.green))
        binding.swipeRefreshLayout.setOnRefreshListener {
            Handler().postDelayed({
                refreshList()
                binding.swipeRefreshLayout.isRefreshing = false
            }, 2000)
        }
    }

    private fun initRecyclerView() {
        binding.recyclerViewPhotos.apply {
            layoutManager = LinearLayoutManager(this@PhotoActivity)
            photoAdapter = PhotoAdapter()
            adapter = photoAdapter
        }
    }

    private fun refreshList() {
        val viewModel = ViewModelProvider(this).get(PhotoActivityViewModel::class.java)
        lifecycleScope.launchWhenCreated {
            viewModel.getListData().collectLatest {
                photoAdapter.submitData(it)
            }
        }
    }
}