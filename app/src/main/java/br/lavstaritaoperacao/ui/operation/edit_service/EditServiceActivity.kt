package br.lavstaritaoperacao.ui.operation.edit_service

import android.Manifest
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.lavstaritaoperacao.R
import br.lavstaritaoperacao.aux.PermissionUtils
import br.lavstaritaoperacao.databinding.ActivityEditServiceBinding
import br.lavstaritaoperacao.domain.model.Item
import br.lavstaritaoperacao.domain.model.Service
import br.lavstaritaoperacao.domain.model.StatusPayment
import br.lavstaritaoperacao.domain.model.StatusService
import br.lavstaritaoperacao.domain.model.emptyService
import br.lavstaritaoperacao.ui.operation.add_service.ItemsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditServiceActivity: AppCompatActivity() {

    private lateinit var binding: ActivityEditServiceBinding
    private val viewModel: EditServiceViewModel by viewModel()
    private lateinit var service: Service
    private lateinit var itemsAdapter: ItemsAdapter
    private lateinit var serviceStatus: StatusService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupListeners()
        setupObservers()
    }

    private fun setupView(){
        binding.rvItems.setHasFixedSize(true)
        binding.rvItems.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        service = intent.getSerializableExtra("service") as Service ?: emptyService()
        serviceStatus = service.statusService ?: StatusService.EM_PASSAGEM

        service.let {
            binding.txtClient.text = it.clientName
            binding.txtPhone.text = it.clientPhone
            viewModel.getAllItemsFromServiceId(serviceId = it.serviceId ?: 0)
            binding.edtObsDetail.setText(it.obs.toString())
            binding.txtPrice.text = "Orçamento " + it.price
            binding.txtPrice.setTextColor(this.getColor(R.color.red_nirvana))
            setStatus(serviceStatus = it.statusService ?: StatusService.EM_PASSAGEM)
        }

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

        binding.btnUpdate.setOnClickListener {
            val obs = binding.edtObsDetail.text.toString()

            viewModel.updateService(service =
                Service(
                    serviceId = service.serviceId ?: 0,
                    clientName = service.clientName,
                    clientPhone = service.clientPhone,
                    qtdItems = service.qtdItems,
                    statusService = serviceStatus ?: StatusService.EM_LAVAGEM,
                    statusPayment = StatusPayment.NOT_PAID,
                    dataIn = service.dataIn,
                    obs = obs,
                    price = service.price
                )
            )
        }

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val radioButton = findViewById<RadioButton>(checkedId)
            val service = radioButton.text.toString()

            serviceStatus = when(service){
                "Lavar" -> StatusService.EM_LAVAGEM
                "Secar" -> StatusService.EM_SECAGEM
                "Passar" -> StatusService.EM_PASSAGEM
                "Concluído" -> StatusService.DONE
                else -> StatusService.EM_PASSAGEM
            }
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

        viewModel.onSuccess.observe(this){
            setupRVItems(it)
        }
    }

    private fun setupRVItems(items: List<Item>){
        itemsAdapter = ItemsAdapter(data = items, singleClick, longClick)
        binding.rvItems.adapter = itemsAdapter
    }

    private val singleClick = { item: Item ->
        Toast.makeText(
            this,
            item.name + ":" + item.qtd.toString() + " : " + item.serviceId,
            Toast.LENGTH_SHORT
        ).show()
    }

    private val longClick = { item: Item ->
        //viewModel.deleteItem(item = item)
    }

    private fun setStatus(serviceStatus : StatusService){
        when(serviceStatus){
            StatusService.EM_LAVAGEM -> binding.lavagemRadioButton.isChecked = true
            StatusService.EM_SECAGEM -> binding.secagemRadioButton.isChecked = true
            StatusService.EM_PASSAGEM -> binding.passagemRadioButton.isChecked = true
            StatusService.DONE -> binding.concluidoRadioButton.isChecked = true
            else -> binding.lavagemRadioButton.isChecked = true
        }
    }

}