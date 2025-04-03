package br.lavstaritaoperacao.ui.client.home_client

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.lavstaritaoperacao.domain.model.Pessoa
import br.lavstaritaoperacao.domain.model.Service2
import br.lavstaritaoperacao.domain.usecase.GetServiceUseCase
import br.lavstaritaoperacao.domain.usecase.InsertServiceUseCase
import kotlinx.coroutines.launch
import kotlin.random.Random


class ClientHomeViewModel(
    private val getServiceUseCase: GetServiceUseCase,
    private val insertServiceUseCase: InsertServiceUseCase
) : ViewModel() {

//    val onResult: LiveData<List<Pessoa>> get() = _onResult
//    private val _onResult: MutableLiveData<List<Pessoa>> = MutableLiveData()

    private val _notes = MutableLiveData<List<Service2>>()
    val notes: LiveData<List<Service2>> = _notes


    fun obterPessoas() {
        viewModelScope.launch {
            getServiceUseCase().fold(
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
            created_at = "2025-03-22 15:39:00.330782+00",
            qtdItems = Random.nextInt(),
            clientPhone = "(11)777777777"
        )
        viewModelScope.launch {
            insertServiceUseCase(randomService).fold(
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