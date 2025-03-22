package br.lavstaritaoperacao.ui.operation.home_operation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.lavstaritaoperacao.domain.model.Service
import br.lavstaritaoperacao.domain.model.StatusService
import br.lavstaritaoperacao.domain.usecase.GlobalUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OperationViewModel(
    private val globalUseCase: GlobalUseCase
): ViewModel() {

    val onLoading: LiveData<Boolean> get() = _onLoading
    private val _onLoading: MutableLiveData<Boolean> = MutableLiveData()

    val onError: LiveData<Boolean> get() = _onError
    private val _onError: MutableLiveData<Boolean> = MutableLiveData()

    val onSuccess: LiveData<List<Service>> get() = _onSuccess
    private val _onSuccess: MutableLiveData<List<Service>> = MutableLiveData()

    fun getAllServicesBy(status: StatusService){
        viewModelScope.launch {
            _onLoading.value = true
            when(status){
                StatusService.CONCLUIDO -> _onSuccess.value = globalUseCase.getALLServices().filter { it.statusService == StatusService.CONCLUIDO }
                StatusService.EM_LAVAGEM, ->  _onSuccess.value = globalUseCase.getALLServices().filter { it.statusService != StatusService.CONCLUIDO }
                else -> _onSuccess.value = globalUseCase.getALLServices()
            }
            delay(500)
            _onLoading.value = false
        }
    }

    fun deleteService(service: Service){
        viewModelScope.launch {
            _onLoading.value = true
            globalUseCase.deleteService(service = service)
            delay(100)
            _onSuccess.value = globalUseCase.getALLServices()
            delay(500)
            _onLoading.value = false
        }
    }

}