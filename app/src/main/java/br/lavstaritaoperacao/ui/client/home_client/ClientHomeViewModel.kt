package br.lavstaritaoperacao.ui.client.home_client

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.lavstaritaoperacao.domain.model.Service2
import br.lavstaritaoperacao.domain.usecase.ServiceUseCase
import kotlinx.coroutines.launch
import kotlin.random.Random


class ClientHomeViewModel(
//    private val getServiceUseCase: GetServiceUseCase,
//    private val insertServiceUseCase: InsertServiceUseCase
    private val serviceUseCase: ServiceUseCase
) : ViewModel() {

//    val onResult: LiveData<List<Pessoa>> get() = _onResult
//    private val _onResult: MutableLiveData<List<Pessoa>> = MutableLiveData()

    private val _notes = MutableLiveData<List<Service2>>()
    val notes: LiveData<List<Service2>> = _notes


    fun obterPessoas() {
        viewModelScope.launch {
            serviceUseCase.getServices().fold(
                onSuccess = { notes ->
                    _notes.value = notes
                },
                onFailure = { error ->
                    println("Erro ao obter notas: ${error.message}")
                }
            )
        }
    }

    fun insertService(){
        val randomService = Service2(
            id = Random.nextInt(),
            clientName = "Teste Teste Teste",
            qtdItems = Random.nextInt(),
            clientPhone = "(11)777777777"
        )
        viewModelScope.launch {
            serviceUseCase.insertService(randomService).fold(
                onSuccess = { insertedNote ->
                    println("Nota inserida com sucesso: ${insertedNote.clientName}")
                },
                onFailure = { error ->
                    println("Erro ao inserir nota: ${error.message}")
                }
            )
        }
    }

}