package br.lavstaritaoperacao.ui.operation.edit_service

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import br.lavstaritaoperacao.aux.PermissionUtils
import br.lavstaritaoperacao.databinding.ActivityEditServiceBinding
import br.lavstaritaoperacao.domain.model.Service
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditServiceActivity: AppCompatActivity() {

    private lateinit var binding: ActivityEditServiceBinding
    private val viewModel: EditServiceViewModel by viewModel()
    private lateinit var service: Service

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupListeners()
        setupObservers()
    }

    private fun setupView(){
        service = intent.getSerializableExtra("service") as Service
        Toast.makeText(this, "Client: ${service.clientName}", Toast.LENGTH_SHORT).show()
    }


    private fun setupListeners(){
        binding.btnVoltar.setOnClickListener {
            super.onBackPressed()
        }
        binding.btnImprimir.setOnClickListener {
            if(!PermissionUtils.verifyPermissions(this, 999)){
                viewModel.printService(service = service)
            } else{
                ActivityCompat.requestPermissions(this, arrayOf(
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.BLUETOOTH_ADMIN
                ), 1)
            }
        }
    }

    private fun setupObservers(){

    }
}