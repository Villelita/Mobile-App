package com.zooquest.zooquest.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zooquest.zooquest.repository.UsersRepository
import com.zooquest.zooquest.viewModel.UsersViewModel

class UsersViewModelFactory(private val repository: UsersRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UsersViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}