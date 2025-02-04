package br.lavstaritaoperacao.data.shared

import br.lavstaritaoperacao.domain.model.Item

interface SharedPrefDataSource {

    suspend fun saveItem(item: Item)
    suspend fun getItems(): Item
//    suspend fun setDataMode(mode: String)
//    suspend fun getDataMode(): String
//    suspend fun saveProfile(profile: StartApp)
//    suspend fun getProfile(): StartApp

}