package br.lavstaritaoperacao.ui.operation.add_service

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.lavstaritaoperacao.R
import br.lavstaritaoperacao.aux.MaskCPF
import br.lavstaritaoperacao.aux.formatCurrency
import br.lavstaritaoperacao.aux.getDateAndTime
import br.lavstaritaoperacao.aux.hideKeyboard
import br.lavstaritaoperacao.databinding.ActivityAddServiceBinding
import br.lavstaritaoperacao.databinding.DialogAddItemBinding
import br.lavstaritaoperacao.domain.model.Item
import br.lavstaritaoperacao.domain.model.Service
import br.lavstaritaoperacao.domain.model.StatusPayment
import br.lavstaritaoperacao.domain.model.StatusService
import br.lavstaritaoperacao.domain.model.gerarNomeDeRoupaAleatorio
import br.lavstaritaoperacao.domain.model.roupas
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.random.Random

class AddServiceActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddServiceBinding
    private val viewModel: AddServiceViewModel by viewModel()
    private lateinit var itemsAdapter: ItemsAdapter
    private lateinit var clientName: String
    private lateinit var clientPhone: String
    private lateinit var clientObs: String
    private lateinit var clientPrice: String
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
        binding.edtClientFone.addTextChangedListener(MaskCPF.mask("(##) #####-####", binding.edtClientFone))
        formatCurrency(binding.edtPriceDetail)
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
            viewModel.cleanNotUsedItems(temporaryServiceId = serviceId ?: 0)
            super.onBackPressed()
        }

        binding.btnIncluirItem.setOnClickListener{
            dialogAddItem()
        }

        binding.btnCreateService.setOnClickListener {
            clientName = binding.edtClientName.text.toString()
            clientPhone = binding.edtClientFone.text.toString()
            clientObs = binding.edtObsDetail.text.toString()
            clientPrice = binding.edtPriceDetail.text.toString()

            viewModel.createService(
                service = Service(
                    serviceId = serviceId,
                    clientName = clientName,
                    clientPhone = clientPhone,
                    qtdItems = items.size ?: 0,
                    dataIn = getDateAndTime(),
                    statusService = StatusService.EM_LAVAGEM,
                    statusPayment = StatusPayment.NOT_PAID,
                    obs = clientObs,
                    price = clientPrice
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

    private fun dialogAddItem(){
        val customDialog = AlertDialog.Builder(this).create()
        val bind : DialogAddItemBinding = DialogAddItemBinding.inflate(LayoutInflater.from(this))
        val containerButtons = findViewById<GridLayout>(R.id.containerItems)
        var qtdItems: Int = 1
        customDialog.apply {
            setView(bind.root)
            setCancelable(true)
        }.show()

        roupas().forEach { roupa ->
            val botao = Button(this)
            botao.text = roupa
            botao.textSize = 12f
            botao.background = AppCompatResources.getDrawable(this, R.drawable.button_style)
            botao.isEnabled = true
            botao.setOnClickListener {
                it.isEnabled = true
                bind.edtDetail.setText(roupa)
                //bind.qtdItems.text = qtdItems.toString()
            }

            val params = GridLayout.LayoutParams()
            params.width = 0 // Permite que o GridLayout controle a largura
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f) // Peso da coluna
            //params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f) // Peso da linha
            val margin = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                8f,
                resources.displayMetrics
            ).toInt()
            params.setMargins(margin, margin, margin, margin)

            botao.layoutParams = params
            bind.containerItems.addView(botao)
        }

        bind.btnAddItem.setOnClickListener {
            hideKeyboard()
            viewModel.addItem(item = Item(
                name = bind.edtDetail.text.toString(),
                qtd = qtdItems ?: 1,
                serviceId = serviceId
            ))
            customDialog.dismiss()
        }

        bind.btnSkipItem.setOnClickListener {
            customDialog.dismiss()
        }

        bind.btnIncrement.setOnClickListener {
            qtdItems += 1
            bind.qtdItems.text = qtdItems.toString()
        }

        bind.btnDecrement.setOnClickListener {
            if(qtdItems <= 1){
                //dont do anything
            } else{
                qtdItems -= 1
                bind.qtdItems.text = qtdItems.toString()

            }

        }

    }
}