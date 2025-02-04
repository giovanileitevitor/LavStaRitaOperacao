package br.lavstaritaoperacao.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    val onSuccess: LiveData<String> get() = _onSuccess
    private val _onSuccess: MutableLiveData<String> = MutableLiveData()

    fun checkCpf(cpf: String){
        viewModelScope.launch {
            _onLoading.value = true
            delay(1000)
            when(globalUseCase.startLogin(cpf = cpf) ?: false){
                true -> {
                    _onSuccess.value = "VALID"
                }
                false -> {
                    _onError.value = true
                }
            }

            _onLoading.value = false
        }
    }
}