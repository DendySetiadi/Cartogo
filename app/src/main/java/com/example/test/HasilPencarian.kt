package com.example.test

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.databinding.HasilPencarianBinding
import com.google.firebase.firestore.FirebaseFirestore

class HasilPencarian : Fragment(R.layout.hasil_pencarian) {

    private lateinit var binding: HasilPencarianBinding
    private lateinit var mobilAdapter: MobilAdapter
    private lateinit var firestore: FirebaseFirestore
    private var isDenganSupir: Boolean = false

    companion object {
        private const val ARG_IS_DENGAN_SUPIR = "is_dengan_supir"

        fun newInstance(isDenganSupir: Boolean): HasilPencarian {
            val fragment = HasilPencarian()

            val args = Bundle()
            args.putBoolean(ARG_IS_DENGAN_SUPIR, isDenganSupir)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HasilPencarianBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firestore = FirebaseFirestore.getInstance()
        mobilAdapter = MobilAdapter(emptyList())

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mobilAdapter
        }

        isDenganSupir = arguments?.getBoolean(ARG_IS_DENGAN_SUPIR) ?: false
        ambilDanTampilkanDataMobil()
    }

    private fun ambilDanTampilkanDataMobil() {
        val collectionPath = if (isDenganSupir) "mobil_supir" else "mobil"

        firestore.collection(collectionPath)
            .get()
            .addOnSuccessListener { result ->
                val mobilList = mutableListOf<Mobil>()

                for (document in result) {
                    val mobil = document.toObject(Mobil::class.java)
                    mobilList.add(mobil)
                }

                Log.d("Firestore", "Jumlah mobil di Firestore: ${mobilList.size}")
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
        }
    }
}
