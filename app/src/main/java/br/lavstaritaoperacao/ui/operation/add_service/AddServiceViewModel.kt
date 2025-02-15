package br.lavstaritaoperacao.ui.operation.add_service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.lavstaritaoperacao.domain.model.Item
import br.lavstaritaoperacao.domain.model.Service
import br.lavstaritaoperacao.domain.usecase.GlobalUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AddServiceViewModel(
    private val globalUseCase: GlobalUseCase
): ViewModel() {

    val onLoading: LiveData<Boolean> get() = _onLoading
    private val _onLoading: MutableLiveData<Boolean> = MutableLiveData()

    val onError: LiveData<String> get() = _onError
    private val _onError: MutableLiveData<String> = MutableLiveData()

    val onSuccess: LiveData<Boolean> get() = _onSuccess
    private val _onSuccess: MutableLiveData<Boolean> = MutableLiveData()

    val itemsAdded: LiveData<List<Item>> get() = _itemsAdded
    private val _itemsAdded: MutableLiveData<List<Item>> = MutableLiveData()

    private var nextGroupId: Int = 1
    val nextServiceId: LiveData<Int> get() = _nextServiceId
    private val _nextServiceId: MutableLiveData<Int> = MutableLiveData()

//    init {
//        viewModelScope.launch {
//            nextGroupId = globalUseCase.getNextGroupId()
//        }
//    }

    fun getNextServiceId(){
        viewModelScope.launch {
            _nextServiceId.value = globalUseCase.getNextGroupId() ?: 0
        }
    }

    fun createService(service: Service){
        viewModelScope.launch {
            _onLoading.value = true
            if(service.clientName == "" || service.clientPhone == "" || service.qtdItems == 0 || service.price.isBlank()){
                _onError.value = "Preencha todos os campos no cadastro !!!"
            }else{
                globalUseCase.addService(service = service)
                refreshItem()
            }
            delay(500)
            _onLoading.value = false
        }
    }

    fun addItem(item: Item){
        viewModelScope.launch {
            _onLoading.value = true
            globalUseCase.addItem(item = item)
            refreshScreen(id = item.serviceId)
            delay(1000)
            _onLoading.value = false
        }
    }

    fun deleteItem(item: Item){
        viewModelScope.launch {
            _onLoading.value = true
            globalUseCase.deleteItem(item = item)
            refreshScreen()
            _onLoading.value = false
        }
    }

    private fun refreshScreen(id: Int? = 0){
        viewModelScope.launch {
            _onLoading.value = true
            _itemsAdded.value = globalUseCase.getItemsByServiceId(serviceId = id ?: 0)
            _onLoading.value = false
        }
    }

    private fun refreshItem(){
        viewModelScope.launch {
            _onLoading.value = true
            val ack = globalUseCase.getALLServices().size ?: 0
            _onSuccess.value = if(ack >= 1) true else false
            _onLoading.value = false
        }
    }

}