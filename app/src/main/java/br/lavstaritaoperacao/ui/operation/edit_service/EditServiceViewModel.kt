package br.lavstaritaoperacao.ui.operation.edit_service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.lavstaritaoperacao.aux.returnText
import br.lavstaritaoperacao.domain.model.Service
import br.lavstaritaoperacao.domain.usecase.GlobalUseCase
import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EditServiceViewModel(
    private val globalUseCase: GlobalUseCase
): ViewModel() {

    val onLoading: LiveData<Boolean> get() = _onLoading
    private val _onLoading: MutableLiveData<Boolean> = MutableLiveData()

    val onError: LiveData<Boolean> get() = _onError
    private val _onError: MutableLiveData<Boolean> = MutableLiveData()

    val onSuccess: LiveData<List<Service>> get() = _onSuccess
    private val _onSuccess: MutableLiveData<List<Service>> = MutableLiveData()

    val onExcludeSuccess: LiveData<Boolean> get() = _onExcludeSuccess
    private val _onExcludeSuccess: MutableLiveData<Boolean> = MutableLiveData()


    fun printService(service: Service){
        viewModelScope.launch {
            _onLoading.value = true
            delay(500)
            val escPrinter = EscPosPrinter(BluetoothPrintersConnections.selectFirstPaired(), 80, 48f, 32)
            escPrinter.printFormattedText(returnText())
            _onLoading.value = false
        }
    }

    fun deleteService(service: Service){
        viewModelScope.launch {
            _onLoading.value = true
            globalUseCase.deleteService(service = service)
            _onExcludeSuccess.value = true
            _onLoading.value = false
        }
    }

}