package br.lavstaritaoperacao.data.db

import br.lavstaritaoperacao.domain.model.Item
import br.lavstaritaoperacao.domain.model.Service

interface LocalRepository {
    suspend fun insertItem(item: Item)
    suspend fun getItems(): List<Item>
    suspend fun getServices(): List<Service>
}