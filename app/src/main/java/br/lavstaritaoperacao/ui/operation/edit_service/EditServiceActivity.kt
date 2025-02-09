package br.lavstaritaoperacao.ui.operation.edit_service

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
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
        Toast.makeText(this, "Client: ${service.clientName} : ServiceId: ${service.serviceId.toString()}", Toast.LENGTH_SHORT).show()
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
        binding.btnExcluir.setOnClickListener {
            viewModel.deleteService(service = service)
        }
    }

    private fun setupObservers(){
        viewModel.onLoading.observe(this){
            binding.loadingBox.root.isVisible = it == true
        }

        viewModel.onExcludeSuccess.observe(this){
            if(it){
                Toast.makeText(this, "Serviço: ${service.serviceId} excluído !", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}