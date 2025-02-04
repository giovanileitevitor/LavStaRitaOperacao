package br.lavstaritaoperacao.ui.add_service

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.lavstaritaoperacao.R
import br.lavstaritaoperacao.aux.Mask
import br.lavstaritaoperacao.databinding.ActivityAddServiceBinding
import br.lavstaritaoperacao.domain.model.Item
import br.lavstaritaoperacao.domain.model.Service
import br.lavstaritaoperacao.domain.model.emptyService
import br.lavstaritaoperacao.domain.model.genericItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddServiceActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddServiceBinding
    private val viewModel: AddServiceViewModel by viewModel()
    private lateinit var itemsAdapter: ItemsAdapter
    private lateinit var items: List<Item>

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
    }

    private fun setupListeners(){
        binding.btnVoltar.setOnClickListener {
            super.onBackPressed()
        }

        binding.btnCancelar.setOnClickListener {
            super.onBackPressed()
        }

        binding.btnIncluirItem.setOnClickListener{
            viewModel.addItem(item = genericItem())
        }

        binding.btnCreateService.setOnClickListener {
            viewModel.createService(
                service = Service(
                    id = 0,
                    qtd = items.size ?: 0,
                    client = "",
                    dataIn = "11 / Jan / 2025"
            )
            )
        }

    }

    private fun setupObservers() {
        viewModel.onLoading.observe(this){
            binding.loadingBox.root.isVisible = it == true
        }
        viewModel.onError.observe(this){

        }
        viewModel.onSuccess.observe(this){

        }
        viewModel.itemsAdded.observe(this){
            setupRVItems(it)
            items = it
        }
    }

    private fun setupRVItems(items: List<Item>){
        binding.labelItems.text = getString(R.string.label_client_itens, items.size.toString() ?: "0")
        binding.rvItems.setHasFixedSize(true)
        binding.rvItems.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        itemsAdapter = ItemsAdapter(data = items, singleClick)
        binding.rvItems.adapter = itemsAdapter
    }

    private val singleClick = { item: Item ->
        Toast.makeText(this, item.name, Toast.LENGTH_SHORT).show()
    }
}