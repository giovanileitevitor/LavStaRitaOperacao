package br.lavstaritaoperacao.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.lavstaritaoperacao.domain.model.LoginResult
import br.lavstaritaoperacao.domain.model.UserType
import br.lavstaritaoperacao.domain.usecase.GlobalUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(
    private val globalUseCase: GlobalUseCase
): ViewModel() {

    val onLoading: LiveData<Boolean> get() = _onLoading
    private val _onLoading: MutableLiveData<Boolean> = MutableLiveData()

    val onError: LiveData<Boolean> get() = _onError
    private val _onError: MutableLiveData<Boolean> = MutableLiveData()

    val onSuccess: LiveData<LoginResult> get() = _onSuccess
    private val _onSuccess: MutableLiveData<LoginResult> = MutableLiveData()

    fun checkCpf(cpf: String){
        viewModelScope.launch {
            _onLoading.value = true
            delay(500)
            val loginResult = globalUseCase.startLogin(cpf = cpf)

            if((loginResult.status == false) && (loginResult.codeResponse == 0)){
                _onError.value = true
            }else{
                _onSuccess.value = loginResult
            }
            _onLoading.value = false
        }
    }
}