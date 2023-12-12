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
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.TimePicker
import androidx.cardview.widget.CardView
import androidx.core.widget.NestedScrollView
import com.example.test.databinding.FragmentLepaskunciBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.bumptech.glide.Glide
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLepaskunciBinding.inflate(inflater, container, false)
        val kabupaten = resources.getStringArray(R.array.kabupaten)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_kabupaten, kabupaten)
        binding.autoComplete.setAdapter(arrayAdapter)
        // Initialize firestore here
        firestore = FirebaseFirestore.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tanggalPengambilan = binding.root.findViewById(R.id.tanggalPengambilan)
        tanggalPengembalian = binding.root.findViewById(R.id.tanggalPengembalian)
        kotak_pengambilan = binding.root.findViewById(R.id.kotak_pengambilan)
        kotak_pengembalian = binding.root.findViewById(R.id.kotak_pengembalian)
        kotakjam = binding.root.findViewById(R.id.kotakjam)
        jam = binding.root.findViewById(R.id.jam)
        kotakjam1 = binding.root.findViewById(R.id.kotakjam1)
        jam1 = binding.root.findViewById(R.id.jam1)

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

            cariDanTampilkanDataMobil(lokasiRental, tanggalPengambilan, jamPengambilan, tanggalPengembalian, jamPengembalian)
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
        firestore.collection("mobil")
            .whereEqualTo("lokasi", lokasiRental)
            .get()
            .addOnSuccessListener { result ->
                val mobilList = mutableListOf<Mobil>()

                for (document in result) {
                    val mobil = document.toObject(Mobil::class.java)
                    mobilList.add(mobil)
                }

                tampilkanDataKeUI(mobilList)
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Gagal mengambil data mobil: $exception")
            }
    }

    private fun tampilkanDataKeUI(mobilList: List<Mobil>) {
        val linearLayout = view?.findViewById<LinearLayout>(R.id.linearLayoutContainer)

        for (mobil in mobilList) {
            Log.d("Firestore", "Nama Mobil: ${mobil.nama}, Gambar: ${mobil.gambar}, Harga: ${mobil.harga}")

            val cardView = layoutInflater.inflate(R.layout.cardview_mobil, null) as CardView
            val imageMobil = cardView.findViewById<ImageView>(R.id.imageMobil)
            val textMerk = cardView.findViewById<TextView>(R.id.textMerk)
            val textHarga = cardView.findViewById<TextView>(R.id.textHarga)

            val imageUrl = if (mobil.gambar.isNotEmpty()) mobil.gambar else "drawable/placeholder_image"

            Glide.with(requireContext())
                .load(imageUrl)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .into(imageMobil)

            textMerk.text = mobil.nama
            // Periksa tipe harga dan konversi ke String
            val hargaString = when (mobil.harga) {
                is String -> mobil.harga
                is Long -> mobil.harga.toString()
                else -> ""
            }
            textHarga.text = "Rp.${hargaString}"

            linearLayout?.addView(cardView)

        }

    }
}
