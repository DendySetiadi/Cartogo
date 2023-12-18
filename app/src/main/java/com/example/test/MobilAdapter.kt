package com.example.test

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test.databinding.CardviewMobilBinding

class MobilAdapter(private var mobilList: List<Mobil>) :
    RecyclerView.Adapter<MobilAdapter.ViewHolder>() {

    private var recyclerView: RecyclerView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CardviewMobilBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        // Set margin untuk CardView
        val layoutParams = binding.root.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.setMargins(8, 8, 8, 8) // Sesuaikan nilai margin sesuai kebutuhan
        binding.root.layoutParams = layoutParams

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mobil = mobilList[position]
        holder.bind(mobil)
    }

    override fun getItemCount(): Int {
        return mobilList.size
    }

    fun updateData(newMobilList: List<Mobil>) {
        mobilList = newMobilList
        notifyDataSetChanged()

        // Menggulir RecyclerView ke posisi terakhir
        recyclerView?.smoothScrollToPosition(itemCount - 1)
    }

    inner class ViewHolder(private val binding: CardviewMobilBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(mobil: Mobil) {
            binding.apply {
                textMerk.text = mobil.nama
                textHarga.text = mobil.harga.toString()

                // Load image using Glide or any other image loading library
                Glide.with(root.context)
                    .load(mobil.gambar)
                    .into(imageMobil)
            }
        }
    }

    // Fungsi ini digunakan untuk mengatur referensi RecyclerView
    fun setRecyclerView(recyclerView: RecyclerView?) {
        this.recyclerView = recyclerView
    }
}
