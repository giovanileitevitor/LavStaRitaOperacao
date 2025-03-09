package br.lavstaritaoperacao.ui.operation.configuration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.lavstaritaoperacao.domain.model.Item
import br.lavstaritaoperacao.domain.model.Service
import br.lavstaritaoperacao.domain.usecase.GlobalUseCase
import kotlinx.coroutines.launch

class ConfigurationViewModel(
    private val globalUseCase: GlobalUseCase
) : ViewModel() {

    val onLoading: LiveData<Boolean> get() = _onLoading
    private val _onLoading: MutableLiveData<Boolean> = MutableLiveData()

    val onError: LiveData<String> get() = _onError
    private val _onError: MutableLiveData<String> = MutableLiveData()

    val servicesAdded: LiveData<List<Service>> get() = _servicesAdded
    private val _servicesAdded: MutableLiveData<List<Service>> = MutableLiveData()

    val itemsAdded: LiveData<List<Item>> get() = _itemsAdded
    private val _itemsAdded: MutableLiveData<List<Item>> = MutableLiveData()

    fun getAllServices(){
        viewModelScope.launch {
            _servicesAdded.value = globalUseCase.getALLServices()
        }
    }

    fun getAllItems(){
        viewModelScope.launch {
            _itemsAdded.value = globalUseCase.getAllItems()
        }
    }

    fun deleteAll(){
        viewModelScope.launch {
            _onLoading.value = true
            globalUseCase.deleteAllServices()
            globalUseCase.deleteAllItems()

            getAllServices()
            getAllItems()
            _onLoading.value = false
        }
    }
}