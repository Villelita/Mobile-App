package com.zooquest.zooquest.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.zooquest.zooquest.data.models.Users
import com.zooquest.zooquest.repository.UsersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsersViewModel(private val repo: UsersRepository) : ViewModel() {
    val usuarios = repo.usuarios.asLiveData()

    private val _insertExitoso = MutableStateFlow(false)
    val insertExitoso: StateFlow<Boolean> = _insertExitoso.asStateFlow()

    private val _user = MutableLiveData<Users?>()
    val user: LiveData<Users?> = _user

    fun agregar(nombre: String, password: String, email:String, birth: String) {
        viewModelScope.launch {
            repo.insertar(Users(name = nombre, password = password, email = email, birth_date = birth))
            _insertExitoso.value = true
        }
    }

    fun doLogin(email:String, password: String) {
        viewModelScope.launch {
            val usuario = repo.findUser(email = email, password = password)
            _user.value = usuario
        }
    }
}