package me.dio.businesscard.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import me.com.dio.businesscard.ui.MainViewModel
import me.com.dio.businesscard.ui.MainViewModelFactory
import me.dio.businesscard.App
import me.dio.businesscard.R
import me.dio.businesscard.data.BusinessCard
import me.dio.businesscard.databinding.ActivityAddBusinessCardBinding


class AddBusinessCardActivity : AppCompatActivity() {
    lateinit var selectedColor: ColorObject

    private val binding by lazy { ActivityAddBusinessCardBinding.inflate(layoutInflater) }

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as App).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        insertListeners()
        loadColorSpinner()

    }

    private fun insertListeners() {
        binding.btnConfirm.setOnClickListener {
            val businessCard = BusinessCard(
                nome = binding.tilNome.editText?.text.toString(),
                empresa = binding.tilEmpresa.editText?.text.toString(),
                telefone = binding.tilTelefone.editText?.text.toString(),
                email = binding.tilEmail.editText?.text.toString(),
               fundoPersonalizado = binding.tilColor.editText?.text.toString()

            )
            mainViewModel.insert(businessCard)
            Toast.makeText(this, R.string.label_show_success, Toast.LENGTH_SHORT).show()
            finish()
        }



        binding.btnClose.setOnClickListener {
            finish()
        }

    }
    private fun loadColorSpinner() {
        selectedColor = ColorList().defaultColor
        binding.colorSpinner.apply {
            adapter = ColorSpinnerAdapter(applicationContext, ColorList().basicColors())
            setSelection(ColorList().colorPosition(selectedColor), false)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long
                ) {
                    selectedColor = ColorList().basicColors()[position]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }
    }
}
