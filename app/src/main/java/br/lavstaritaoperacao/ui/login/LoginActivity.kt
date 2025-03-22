package br.lavstaritaoperacao.ui.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import br.lavstaritaoperacao.aux.MaskCPF
import br.lavstaritaoperacao.aux.PermissionUtils
import br.lavstaritaoperacao.aux.hideKeyboard
import br.lavstaritaoperacao.aux.onDebouncedListener
import br.lavstaritaoperacao.databinding.ActivityLoginBinding
import br.lavstaritaoperacao.domain.model.UserType
import br.lavstaritaoperacao.ui.client.home_client.ClientHomeActivity
import br.lavstaritaoperacao.ui.operation.home_operation.OperationActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupPermissions()
        setupView()
        setupListeners()
        setupObservers()
    }

    private fun setupPermissions(){
        PermissionUtils.verifyPermissions(this, 1)
    }

    private fun setupView(){
        binding.txtVersion.text = "Version: 1.0.0"
        binding.edtCpf.addTextChangedListener(MaskCPF.mask("###.###.###-##", binding.edtCpf))
        //binding.txtStatus.text = "Awaiting...."
        binding.txtRedes.text = "Saiba mais em:"
    }

    private fun setupObservers(){
        viewModel.onLoading.observe(this) {
            binding.loadingBox.root.isVisible = it == true
        }

        viewModel.onError.observe(this){
            if(it){
                Toast.makeText(this, "Erro de requisição ou CPF inválido!!!", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.onSuccess.observe(this){
            if(it.userType == UserType.CLIENT){
                val intent = Intent(this, ClientHomeActivity::class.java)
                startActivity(intent)
            }
            if(it.userType == UserType.ADMIN){
                val intent = Intent(this, OperationActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setupListeners(){
        binding.btnFacebook.onDebouncedListener {
            val link = "https://www.linkedin.com/in/giovani-leite-vitor-7803961b9/"
            startActivity(
                Intent(
                    Intent.ACTION_VIEW, Uri.parse(link)
                )
            )
        }

        binding.btnWhatsapp.onDebouncedListener {
            val phoneNumber = "+11975313142"
            val message = "Entre em contato comigo, por favor."
            startActivity(
                Intent(
                    Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=$phoneNumber&text=$message"
                    )
                )
            )
        }
        binding.btnInstagram.onDebouncedListener {

        }

        binding.edtGo.setEndIconOnClickListener {
            val cpf = binding.edtCpf.text.trim() ?: "- - -"
            viewModel.checkCpf(cpf = cpf.toString())
            hideKeyboard()
        }

    }

    private fun startHome(){

    }

}