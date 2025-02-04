package br.lavstaritaoperacao.ui.home_operation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.lavstaritaoperacao.databinding.ActivityOperationBinding
import br.lavstaritaoperacao.domain.model.Service
import br.lavstaritaoperacao.ui.add_service.AddServiceActivity
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


    private fun setupView(){
        viewModel.getServices()
    }

    private fun setupListeners(){
        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, AddServiceActivity::class.java)
            startActivity(intent)
        }
        binding.btnAddsService.setOnClickListener {
            val intent = Intent(this, AddServiceActivity::class.java)
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
        binding.rvServices.setHasFixedSize(true)
        binding.rvServices.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        servicesAdapter = ServicesAdapter(data = services, singleClick)
        binding.rvServices.adapter = servicesAdapter
    }

    private val singleClick = { service: Service ->
        Toast.makeText(this, service.client, Toast.LENGTH_SHORT).show()
    }
}