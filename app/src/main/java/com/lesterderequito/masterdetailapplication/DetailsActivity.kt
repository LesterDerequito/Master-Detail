package com.lesterderequito.masterdetailapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.lesterderequito.masterdetailapplication.common.NetworkSettings
import com.lesterderequito.masterdetailapplication.databinding.ActivityDetailsBinding


class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val author = intent.getStringExtra("AUTHOR").toString()
        val uri = intent.getStringExtra("URL").toString()

        binding.textViewAuthor.text = author

        if (NetworkSettings.isConnectionAvailable(this)) {
            Glide.with(this)
                .load(uri)
                .into(binding.imageViewPhoto)

        } else {
            val bitmap: Bitmap?
            if (intent.hasExtra("BYTE")){
                val byteArray = intent.getByteArrayExtra("BYTE")
                if (byteArray != null) {
                    bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                    binding.imageViewPhoto.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 1000, 1000, false))
                }
            }
        }
    }
}