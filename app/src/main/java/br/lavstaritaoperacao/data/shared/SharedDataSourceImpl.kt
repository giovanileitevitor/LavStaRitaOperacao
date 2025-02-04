package br.lavstaritaoperacao.data.shared

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import br.lavstaritaoperacao.domain.model.Item
import com.google.gson.Gson
import kotlinx.coroutines.flow.first

class SharedDataSourceImpl(
    private val context: Context
) : SharedPrefDataSource{

    companion object{
        val ITEM_KEY = stringPreferencesKey("ITEM")
    }


    override suspend fun saveItem(item: Item) {
        val item = Gson().toJson(item, Item::class.java)
        context.dataStore.edit { preferences ->
            preferences[ITEM_KEY] = item
        }
    }

    override suspend fun getItems(): Item {
        val preferences = context.dataStore.data.first()
        val item = preferences[ITEM_KEY]
        return Gson().fromJson(item, Item::class.java)
    }



//    override suspend fun setDataMode(mode: String) {
//        context.dataStore.edit { preferences ->
//            preferences[DATA_MODE] = mode
//        }
//    }
//
//    override suspend fun getDataMode(): String {
//        val preferences = context.dataStore.data.first()
//        return preferences[DATA_MODE] ?: ""
//    }
//
//    override suspend fun saveProfile(profile: StartApp) {
//        val profile = Gson().toJson(profile, StartApp::class.java)
//        context.dataStore.edit { preferences ->
//            preferences[PROFILE] = profile
//        }
//    }
//
//    override suspend fun getProfile(): StartApp {
//        val preferences = context.dataStore.data.first()
//        val profile = preferences[PROFILE]
//        return Gson().fromJson(profile, StartApp::class.java)
//    }

}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "FARM-APP")