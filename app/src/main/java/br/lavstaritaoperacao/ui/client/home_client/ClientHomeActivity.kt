package br.lavstaritaoperacao.ui.client.home_client

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import br.lavstaritaoperacao.databinding.ActivityClientHomeBinding
import br.lavstaritaoperacao.databinding.ActivityLoginBinding
import br.lavstaritaoperacao.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

class ClientHomeActivity: AppCompatActivity() {

    private lateinit var binding : ActivityClientHomeBinding
    //private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityClientHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


}