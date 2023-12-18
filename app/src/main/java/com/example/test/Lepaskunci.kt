package com.example.test


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.databinding.FragmentLepaskunciBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.example.test.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Lepaskunci : Fragment(R.layout.fragment_lepaskunci) {
    private lateinit var binding: FragmentLepaskunciBinding
    private lateinit var tanggalPengambilan: TextView
    private lateinit var tanggalPengembalian: TextView
    private lateinit var kotak_pengambilan: ImageView
    private lateinit var kotak_pengembalian: ImageView
    private lateinit var kotakjam: ImageView
    private lateinit var jam: TextView
    private lateinit var kotakjam1: ImageView
    private lateinit var jam1: TextView
    private lateinit var firestore: FirebaseFirestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var mobilAdapter: MobilAdapter
    private lateinit var mobilList: MutableList<Mobil>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLepaskunciBinding.inflate(inflater, container, false)

        val kabupaten = resources.getStringArray(R.array.kabupaten)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_kabupaten, kabupaten)
        binding.autoComplete.setAdapter(arrayAdapter)

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()

        // Initialize RecyclerView
        recyclerView = binding.root.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize MobilAdapter
        mobilAdapter = MobilAdapter(emptyList())
        recyclerView.adapter = mobilAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tanggalPengambilan = binding.tanggalPengambilan
        tanggalPengembalian = binding.tanggalPengembalian
        kotak_pengambilan = binding.kotakPengambilan
        kotak_pengembalian = binding.kotakPengembalian
        kotakjam = binding.kotakjam
        jam = binding.jam
        kotakjam1 = binding.kotakjam1
        jam1 = binding.jam1

        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel(myCalendar)
        }

        val myCalendarr = Calendar.getInstance()
        val datePickerr = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalendarr.set(Calendar.YEAR, year)
            myCalendarr.set(Calendar.MONTH, month)
            myCalendarr.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabell(myCalendarr)
        }

        kotakjam.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                jam.text = SimpleDateFormat("HH:mm", Locale.UK).format(cal.time)
            }
            TimePickerDialog(requireContext(), timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        kotakjam1.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                jam1.text = SimpleDateFormat("HH:mm", Locale.UK).format(cal.time)
            }
            TimePickerDialog(requireContext(), timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        kotak_pengambilan.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                datePicker,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        kotak_pengembalian.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                datePickerr,
                myCalendarr.get(Calendar.YEAR),
                myCalendarr.get(Calendar.MONTH),
                myCalendarr.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.masuk.setOnClickListener {
            val intent = Intent(requireContext(), Masuk::class.java)
            startActivity(intent)
        }

        binding.tombolcari.setOnClickListener {
            val lokasiRental = binding.autoComplete.text.toString()
            val tanggalPengambilan = binding.tanggalPengambilan.text.toString()
            val jamPengambilan = binding.jam.text.toString()
            val tanggalPengembalian = binding.tanggalPengembalian.text.toString()
            val jamPengembalian = binding.jam1.text.toString()

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
        tanggalPengambilan.text = sdf.format(myCalendar.time)
    }

    private fun updateLabell(myCalendarr: Calendar) {
        val myFormatt = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormatt, Locale.UK)
        tanggalPengembalian.text = sdf.format(myCalendarr.time)
    }

    private fun cariDanTampilkanDataMobil(
        lokasiRental: String,
        tanggalPengambilan: String,
        jamPengambilan: String,
        tanggalPengembalian: String,
        jamPengembalian: String
    ) {
        mobilList = mutableListOf()

        firestore.collection("mobil")
            .whereEqualTo("lokasi", lokasiRental)
            .whereEqualTo("tanggalPengambilan", tanggalPengambilan)
            .whereEqualTo("jamPengambilan", jamPengambilan)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val mobil = document.toObject(Mobil::class.java)
                    mobilList.add(mobil)
                }

                setHasilPencarian(mobilList)

                // Panggil fungsi tambahkanDataMobil setelah menampilkan hasil pencarian
                tambahkanDataMobil(lokasiRental, tanggalPengambilan, jamPengambilan, tanggalPengambilan, jamPengambilan)
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Gagal mengambil data mobil: $exception")
            }
    }

    private fun tambahkanDataMobil(
        lokasiRental: String,
        tanggalPengambilan: String,
        jamPengambilan: String,
        tanggalPengembalian: String,
        jamPengembalian: String
    ) {
        val dataSudahAda = cariDanTampilkanDataMobil(
            lokasiRental,
            tanggalPengambilan,
            jamPengambilan,
            tanggalPengembalian,
            jamPengembalian
        )

        if (dataSudahAda != null && dataSudahAda !=null) {
            val mobil = Mobil(
                nama = "Toyota Avanza",
                gambar = "https://www.toyota.astra.co.id/sites/default/files/2023-09/avanza%20g%20type_2_4.png",
                harga = 150000.0,
                lokasi = lokasiRental,
                tanggalPengambilan = "",
                jamPengambilan = jamPengambilan,
                tanggalPengembalian ="",
                jamPengembalian = ""
            )

            tambahkanDataBaruKeFirestore(mobil)
        } else {
            // Jika data sudah ada, lakukan tindakan sesuai kebutuhan
            // ...
        }
    }

    private fun tambahkanDataBaruKeFirestore(mobil: Mobil) {
        firestore.collection("mobil")
            .add(mobil)
            .addOnSuccessListener { documentReference ->
                Log.d("Firestore", "Dokumen berhasil ditambahkan dengan ID: ${documentReference.id}")
                // Di sini Anda bisa menambahkan tindakan setelah data berhasil ditambahkan, jika diperlukan
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Gagal menambahkan dokumen", exception)
                // Di sini Anda bisa menambahkan tindakan jika penambahan data gagal
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
            Toast.makeText(requireContext(), "Produk tidak tersedia", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showHasilPencarianDenganSupir() {
        val fragment = HasilPencarian.newInstance(true)
        // Tambahkan fragment ke container di aktivitas (atau lakukan transaksi fragment lainnya)
    }

    private fun showHasilPencarianLepasKunci() {
        val fragment = HasilPencarian.newInstance(false)
        // Tambahkan fragment ke container di aktivitas (atau lakukan transaksi fragment lainnya)
    }
}
