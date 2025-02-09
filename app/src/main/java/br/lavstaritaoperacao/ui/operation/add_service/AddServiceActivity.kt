package br.lavstaritaoperacao.ui.operation.add_service

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.lavstaritaoperacao.R
import br.lavstaritaoperacao.aux.Mask
import br.lavstaritaoperacao.aux.getDateAndTime
import br.lavstaritaoperacao.databinding.ActivityAddServiceBinding
import br.lavstaritaoperacao.domain.model.Item
import br.lavstaritaoperacao.domain.model.Service
import br.lavstaritaoperacao.domain.model.gerarNomeDeRoupaAleatorio
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.random.Random

class AddServiceActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddServiceBinding
    private val viewModel: AddServiceViewModel by viewModel()
    private lateinit var itemsAdapter: ItemsAdapter
    private lateinit var clientName: String
    private lateinit var clientPhone: String
    private var items: List<Item> = emptyList()
    private var serviceId: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupListeners()
        setupObservers()
    }

    private fun setupView(){
        binding.edtClientFone.addTextChangedListener(Mask.mask("(##) #####-####", binding.edtClientFone))
        binding.labelItems.text = getString(R.string.label_client_itens, "0")
        binding.labelIdService.text = getString(R.string.label_id_service, "-")
        binding.rvItems.setHasFixedSize(true)
        binding.rvItems.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        viewModel.getNextServiceId()
    }

    private fun setupListeners(){
        binding.btnVoltar.setOnClickListener {
            super.onBackPressed()
        }

        binding.btnCancelar.setOnClickListener {
            super.onBackPressed()
        }

        binding.btnIncluirItem.setOnClickListener{
            viewModel.addItem(
                item = Item(
                    name = gerarNomeDeRoupaAleatorio(),
                    qtd = Random.nextInt(1,10),
                    serviceId = serviceId,
                    obs = " - "
            ))
        }

        binding.btnCreateService.setOnClickListener {
            clientName = binding.edtClientName.text.toString()
            clientPhone = binding.edtClientFone.text.toString()

            viewModel.createService(
                service = Service(
                    serviceId = serviceId,
                    clientName = clientName,
                    clientPhone = clientPhone,
                    qtdItems = items.size ?: 0,
                    dataIn = getDateAndTime()
                )
            )
        }
    }

    private fun setupObservers() {
        viewModel.onLoading.observe(this){
            binding.loadingBox.root.isVisible = it == true
        }

        viewModel.onError.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.nextServiceId.observe(this){
            binding.labelIdService.text = getString(R.string.label_id_service, it.toString() ?: "-")
            serviceId = it ?: 0
        }

        viewModel.onSuccess.observe(this){
            if(it){
                Toast.makeText(this, "Salvo com sucesso !!", Toast.LENGTH_SHORT).show()
                super.onBackPressed()
                finish()
            }
        }

        viewModel.itemsAdded.observe(this){
            setupRVItems(it)
            items = it
        }
    }

    private fun setupRVItems(items: List<Item>){
        binding.labelItems.text = getString(R.string.label_client_itens, items.size.toString() ?: "0")
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
        viewModel.deleteItem(item = item)
    }
}