package com.xaluoqone.practise.data

import android.app.Application
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DataStoreViewModel(application: Application) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(DataStoreUiState())
    val uiState: StateFlow<DataStoreUiState> = _uiState
    private val textKey = stringPreferencesKey("test_key")

    init {
        viewModelScope.launch {
            application.dataStore.data
                .collect { preferences ->
                    _uiState.update {
                        it.copy(text = preferences[textKey] ?: "No data")
                    }
                }
        }
    }

    suspend fun saveText() {
        getApplication<Application>().dataStore.edit { settings ->
            settings[textKey] = "这是保存的测试文本${System.currentTimeMillis()}"
        }
    }
}

data class DataStoreUiState(val text: String = "")