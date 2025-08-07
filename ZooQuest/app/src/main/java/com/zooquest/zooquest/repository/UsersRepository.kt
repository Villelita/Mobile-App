package com.zooquest.zooquest.repository

import com.zooquest.zooquest.data.dao.UsersDAO
import com.zooquest.zooquest.data.models.Users

class UsersRepository(private val dao: UsersDAO) {
    val usuarios = dao.obtenerTodos()

    suspend fun insertar(usuario: Users) = dao.insertar(usuario)
    suspend fun findUser(email: String, password: String): Users? = dao.findUser(email, password)
}