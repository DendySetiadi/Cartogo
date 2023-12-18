package com.example.test
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.HasilPencarian
import com.example.test.Masuk
import com.example.test.Mobil
import com.example.test.MobilAdapter
import com.example.test.R
import com.example.test.databinding.FragmentDenganSupirBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DenganSupir : Fragment(R.layout.fragment_dengan_supir) {
    private lateinit var firestore: FirebaseFirestore
    private lateinit var binding: FragmentDenganSupirBinding
    private lateinit var tanggal: TextView
    private lateinit var kotak_tanggal: ImageView
    private lateinit var kotakjam: ImageView
    private lateinit var jam: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var mobilAdapter: MobilAdapter
    private lateinit var mobilList: MutableList<Mobil>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDenganSupirBinding.inflate(inflater, container, false)
        firestore = FirebaseFirestore.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val kabupaten = resources.getStringArray(R.array.kabupaten)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_kabupaten, kabupaten)
        binding.autoComplete.setAdapter(arrayAdapter)
        tanggal = binding.root.findViewById(R.id.tanggal)
        kotak_tanggal = binding.root.findViewById(R.id.kotak_tanggal)
        kotakjam = binding.root.findViewById(R.id.kotakjam)
        jam = binding.root.findViewById(R.id.jam)

        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel(myCalendar)
        }

        kotakjam.setOnClickListener {
            showTimePickerDialog(jam)
        }

        kotak_tanggal.setOnClickListener {
            showDatePickerDialog(myCalendar, datePicker)
        }

        binding.masuk.setOnClickListener {
            val intent = Intent(requireContext(), Masuk::class.java)
            startActivity(intent)
        }

        binding.tombolcari.setOnClickListener {
            val lokasiRental = binding.autoComplete.text.toString()
            val tanggalPengambilan = binding.tanggal.text.toString()
            val jamPengambilan = binding.jam.text.toString()

            // Tambahkan variabel yang dibutuhkan
            val tanggalPengembalian = ""  // Sesuaikan ini dengan kebutuhan
            val jamPengembalian = ""  // Sesuaikan ini dengan kebutuhan

            // Panggil fungsi untuk mencari dan menampilkan data mobil
            cariDanTampilkanDataMobil(
                lokasiRental,
                tanggalPengambilan,
                jamPengambilan,
                tanggalPengembalian,
                jamPengembalian
            )
        }
    }

    private fun updateLabel(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        tanggal.text = sdf.format(myCalendar.time)
    }

    private fun showTimePickerDialog(targetTextView: TextView) {
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            targetTextView.text = SimpleDateFormat("HH:mm", Locale.UK).format(cal.time)
        }
        TimePickerDialog(
            requireContext(),
            timeSetListener,
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun showDatePickerDialog(
        myCalendar: Calendar,
        datePicker: DatePickerDialog.OnDateSetListener
    ) {
        DatePickerDialog(
            requireContext(),
            datePicker,
            myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun cariDanTampilkanDataMobil(
        lokasiRental: String,
        tanggalPengambilan: String,
        jamPengambilan: String,
        tanggalPengembalian: String,
        jamPengembalian: String
    ) {
        mobilList = mutableListOf() // Inisialisasi di sini

        firestore.collection("mobil")
            .whereEqualTo("lokasi", lokasiRental)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val mobil = document.toObject(Mobil::class.java)
                    mobilList.add(mobil)
                }

                // Panggil setHasilPencarian di sini
                setHasilPencarian(mobilList)
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Gagal mengambil data mobil: $exception")
            }
    }

    private fun setHasilPencarian(mobilList: List<Mobil>) {
        if (mobilList.isNotEmpty()) {
            val hasilPencarianFragment = HasilPencarian()
            hasilPencarianFragment.setHasilPencarian(mobilList)

            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, hasilPencarianFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        } else {
            // Tampilkan pesan "Produk tidak tersedia" menggunakan Toast
            Toast.makeText(requireContext(), "Produk tidak tersedia", Toast.LENGTH_SHORT).show()
        }
    }
}