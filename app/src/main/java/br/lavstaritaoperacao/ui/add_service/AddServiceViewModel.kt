package br.lavstaritaoperacao.ui.add_service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.lavstaritaoperacao.domain.model.Item
import br.lavstaritaoperacao.domain.model.Service
import br.lavstaritaoperacao.domain.model.emptyItemList
import br.lavstaritaoperacao.domain.usecase.GlobalUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AddServiceViewModel(
    private val globalUseCase: GlobalUseCase
): ViewModel() {

    val onLoading: LiveData<Boolean> get() = _onLoading
    private val _onLoading: MutableLiveData<Boolean> = MutableLiveData()

    val onError: LiveData<Boolean> get() = _onError
    private val _onError: MutableLiveData<Boolean> = MutableLiveData()

    val onSuccess: LiveData<Boolean> get() = _onSuccess
    private val _onSuccess: MutableLiveData<Boolean> = MutableLiveData()

    val itemsAdded: LiveData<List<Item>> get() = _itemsAdded
    private val _itemsAdded: MutableLiveData<List<Item>> = MutableLiveData()


    fun createService(service: Service){
        viewModelScope.launch {
            _onLoading.value = true
            _onSuccess.value  = globalUseCase.createService(service = service)
            delay(500)
            _onLoading.value = false
        }
    }

    fun addItem(item: Item){
        viewModelScope.launch {
            _onLoading.value = true
            globalUseCase.addItem(item = item)
            refreshScreen()
            delay(1000)
            _onLoading.value = false
        }
    }

    private fun refreshScreen(){
        viewModelScope.launch {
            _onLoading.value = true
            _itemsAdded.value = globalUseCase.getItems()
            _onLoading.value = false
        }
    }

}