package br.lavstaritaoperacao.ui.operation.configuration

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.lavstaritaoperacao.R
import br.lavstaritaoperacao.databinding.ActivityConfigurationBinding
import br.lavstaritaoperacao.domain.model.Item
import br.lavstaritaoperacao.domain.model.Service
import br.lavstaritaoperacao.ui.operation.add_service.ItemsAdapter
import br.lavstaritaoperacao.ui.operation.home_operation.ServicesAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class ConfigurationActivity: AppCompatActivity() {

    private lateinit var binding: ActivityConfigurationBinding
    private val viewModel: ConfigurationViewModel by viewModel()
    private lateinit var servicesAdapterConfig: ServicesAdapter
    private lateinit var itemsAdapterConfig: ItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigurationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupListeners()
        setupObservers()
    }

    private fun setupView(){
        binding.rvItems.setHasFixedSize(true)
        binding.rvItems.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        binding.rvServices.setHasFixedSize(true)
        binding.rvServices.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        viewModel.getAllServices()
        viewModel.getAllItems()
    }

    private fun setupListeners(){
        binding.btnVoltar.setOnClickListener {
            super.onBackPressed()
        }
        binding.btnDeleteAll.setOnClickListener {
            viewModel.deleteAll()
        }
        binding.btnExportAll.setOnClickListener {
            viewModel.shareAll()
        }
    }

    private fun setupObservers(){
        viewModel.servicesAdded.observe(this){
            setupServicesRV(it)
        }

        viewModel.itemsAdded.observe(this){
            setupRVItems(it)
        }

        viewModel.file.observe(this){

        }
    }

    private fun setupServicesRV(services: List<Service>){
        binding.labelServices.text = getString(R.string.label_services_qtd, services.size.toString() ?: "0")
        servicesAdapterConfig = ServicesAdapter(context = this, data = services, singleClick1, onLongClick1)
        binding.rvServices.adapter = servicesAdapterConfig
    }

    private val singleClick1 = { service: Service ->
        Toast.makeText(
            this,
            service.clientName + ":" + service.qtdItems.toString() + " : " + service.serviceId.toString(),
            Toast.LENGTH_SHORT
        ).show()
    }

    private val onLongClick1 = { service: Service ->
        //viewModel.deleteService(service = service)
    }

    private fun setupRVItems(items: List<Item>){
        binding.labelItems.text = getString(R.string.label_client_itens, items.size.toString() ?: "0")
        itemsAdapterConfig = ItemsAdapter(data = items, singleClick2, longClick2)
        binding.rvItems.adapter = itemsAdapterConfig
    }

    private val singleClick2 = { item: Item ->
        Toast.makeText(
            this,
            item.name + ":" + item.qtd.toString() + " : " + item.serviceId,
            Toast.LENGTH_SHORT
        ).show()
    }

    private val longClick2 = { item: Item ->
        //viewModel.deleteItem(item = item)
    }

    private fun compartilharArquivoExcel() {
        // Obtenha o caminho do arquivo Excel gerado
        val caminhoArquivo = "${getExternalFilesDir(null)}/dados_exportados.xlsx"
        val arquivo = File(caminhoArquivo)

        // Verifique se o arquivo existe
        if (arquivo.exists()) {
            // Crie uma Uri para o arquivo usando FileProvider
            val uri = FileProvider.getUriForFile(
                this,
                "${packageName}.fileprovider", // Substitua pelo seu authority
                arquivo
            )

            // Crie um Intent para compartilhar o arquivo
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" // MIME type do Excel
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // Permita que outros apps leiam o arquivo

            // Inicie o Intent de compartilhamento
            startActivity(Intent.createChooser(intent, "Compartilhar arquivo Excel"))
        }
    }
}