package com.example.test
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.Mobil
import com.example.test.MobilAdapter
import com.example.test.R
import com.example.test.databinding.HasilPencarianBinding
import com.google.firebase.firestore.FirebaseFirestore

class HasilPencarian : Fragment(R.layout.hasil_pencarian) {

    private lateinit var binding: HasilPencarianBinding
    private lateinit var mobilAdapter: MobilAdapter
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HasilPencarianBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mobilAdapter = MobilAdapter(emptyList()) // Inisialisasi adapter dengan daftar kosong

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mobilAdapter
        }

        // Panggil metode untuk mengambil data dari Firestore dan mengupdate hasil pencarian
        ambilDanTampilkanDataMobil()
    }

    override fun onResume() {
        super.onResume()

        // Pindahkan fungsi ini ke onResume jika ingin mengeksekusinya setiap kali fragment ditampilkan
        // ambilDanTampilkanDataMobil()
    }

    private fun ambilDanTampilkanDataMobil() {
        // Implementasikan logika pengambilan data dari Firestore di sini
        // ...

        firestore.collection("mobil")
            .get()
            .addOnSuccessListener { result ->
                val mobilList = mutableListOf<Mobil>()

                for (document in result) {
                    val mobil = document.toObject(Mobil::class.java)
                    mobilList.add(mobil)
                }

                // Cetak jumlah mobil di logcat
                Log.d("Firestore", "Jumlah mobil di Firestore: ${mobilList.size}")

                // Panggil setHasilPencarian untuk mengupdate hasil pencarian
                setHasilPencarian(mobilList)
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Gagal mengambil data mobil: $exception")
            }
    }

    fun setHasilPencarian(mobilList: List<Mobil>) {

            if (::mobilAdapter.isInitialized) {
                mobilAdapter.updateData(mobilList)
            } else {
                // Handle case when mobilAdapter is not initialized
                // You may choose to initialize it here or take other actions
            }
        }

    }

