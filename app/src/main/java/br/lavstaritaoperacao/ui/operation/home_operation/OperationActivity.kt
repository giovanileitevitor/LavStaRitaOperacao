package br.lavstaritaoperacao.ui.operation.home_operation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.lavstaritaoperacao.databinding.ActivityOperationBinding
import br.lavstaritaoperacao.domain.model.Service
import br.lavstaritaoperacao.ui.operation.add_service.AddServiceActivity
import br.lavstaritaoperacao.ui.operation.configuration.ConfigurationActivity
import br.lavstaritaoperacao.ui.operation.edit_service.EditServiceActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class OperationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOperationBinding
    private val viewModel: OperationViewModel by viewModel()
    private lateinit var servicesAdapter: ServicesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOperationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupListeners()
        setupObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getServices()
    }

    private fun setupView(){
        binding.rvServices.setHasFixedSize(true)
        binding.rvServices.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        viewModel.getServices()
    }

    private fun setupListeners(){
        binding.btnAddsService.setOnClickListener {
            val intent = Intent(this, AddServiceActivity::class.java)
            startActivity(intent)
        }
        binding.btnRefresh.setOnClickListener {
            viewModel.getServices()
        }
        binding.btnSearch.setOnClickListener {

        }
        binding.btnConfig.setOnClickListener {
            val intent = Intent(this, ConfigurationActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setupObservers(){
        viewModel.onLoading.observe(this) {
            binding.loadingBox.root.isVisible = it == true
        }

        viewModel.onError.observe(this){

        }

        viewModel.onSuccess.observe(this){
            setupServicesRV(it)
        }
    }

    private fun setupServicesRV(services: List<Service>){
        servicesAdapter = ServicesAdapter(data = services, singleClick, onLongClick)
        binding.rvServices.adapter = servicesAdapter
    }

    private val singleClick = { service: Service ->
        val intent = Intent(this, EditServiceActivity::class.java)
        intent.putExtra("service", service)
        startActivity(intent)
    }

    private val onLongClick = { service: Service ->
        viewModel.deleteService(service = service)
    }
}