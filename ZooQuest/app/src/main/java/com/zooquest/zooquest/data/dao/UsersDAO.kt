package com.zooquest.zooquest.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zooquest.zooquest.data.models.Users
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(usuario: Users)

    @Query("SELECT * FROM usuarios ORDER BY id ASC")
    fun obtenerTodos(): Flow<List<Users>>

    @Query("SELECT * FROM usuarios WHERE email = :email AND password = :password LIMIT 1")
    suspend fun findUser(email: String, password: String): Users?
}