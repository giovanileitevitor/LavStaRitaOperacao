package br.lavstaritaoperacao.data.firestore

import br.lavstaritaoperacao.domain.model.Service

interface FirestoreRepository {

    suspend fun saveService(service: Service)
    //suspend fun getServices(): MutableList<Service>
}