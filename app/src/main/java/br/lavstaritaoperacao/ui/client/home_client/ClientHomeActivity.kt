package br.lavstaritaoperacao.ui.client.home_client

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.lavstaritaoperacao.databinding.ActivityClientHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ClientHomeActivity: AppCompatActivity() {

    private lateinit var binding : ActivityClientHomeBinding
    private val viewModel: ClientHomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupListeners()
        setupObservers()
    }

    private fun setupView(){

    }

    private fun setupListeners(){
        binding.btnInsertData.setOnClickListener {
            viewModel.insertService()
        }

        binding.btnGetList.setOnClickListener {
            viewModel.obterPessoas()
        }
    }

    private fun setupObservers(){
        viewModel.notes.observe(this) {
            binding.qtdItems.text = "Total: ${it.size}"
            binding.txtResult.text = it.toString()
        }
    }


}