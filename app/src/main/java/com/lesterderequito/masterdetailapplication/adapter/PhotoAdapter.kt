package com.lesterderequito.masterdetailapplication.adapter

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.drawToBitmap
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.facebook.shimmer.ShimmerFrameLayout
import com.lesterderequito.masterdetailapplication.R
import com.lesterderequito.masterdetailapplication.network.PhotoInfo
import java.io.ByteArrayOutputStream

class PhotoAdapter : PagingDataAdapter<PhotoInfo, PhotoAdapter.ViewHolder>(DiffUtilCallBack()) {

    var onItemClick: ((String, String, ByteArray) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return ViewHolder(layoutInflater)
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        val imageViewPhoto: ImageView = view.findViewById(R.id.imageViewPhoto)
        private val textViewAuthorName: TextView = view.findViewById(R.id.textViewAuthorName)
        val shimmerFrameLayout: ShimmerFrameLayout = view.findViewById(R.id.shimmerFrameLayout)

        fun bind(item: PhotoInfo) {
            textViewAuthorName.text = item.author
            shimmerFrameLayout.startShimmerAnimation()

            itemView.setOnClickListener {
                val byteArrayOutputStream = ByteArrayOutputStream()
                imageViewPhoto.drawToBitmap().compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream)
                val byteArray = byteArrayOutputStream.toByteArray()
                onItemClick?.invoke(textViewAuthorName.text.toString(),
                    item.downloadUrl.trim(), byteArray)
            }

            android.os.Handler(Looper.myLooper()!!).postDelayed(
                {
                    Glide.with(imageViewPhoto.context)
                        .load(item.downloadUrl)
                        .circleCrop()
                        .listener(object: RequestListener<Drawable> {
                            override fun onLoadFailed(e: GlideException?,
                                                      model: Any?,
                                                      target:
                                                      Target<Drawable>?,
                                                      isFirstResource: Boolean): Boolean {
                                return false
                            }

                            override fun onResourceReady(resource: Drawable?,
                                                         model: Any?,
                                                         target: Target<Drawable>?,
                                                         dataSource: DataSource?,
                                                         isFirstResource: Boolean
                            ): Boolean {

                                shimmerFrameLayout.stopShimmerAnimation()
                                shimmerFrameLayout.visibility = View.GONE
                                return false
                            }
                        })
                        .into(imageViewPhoto)

                }, 500)

        }
    }

    class DiffUtilCallBack: DiffUtil.ItemCallback<PhotoInfo>() {
        override fun areItemsTheSame(oldItem: PhotoInfo, newItem: PhotoInfo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PhotoInfo, newItem: PhotoInfo): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.author == newItem.author
        }

    }
}