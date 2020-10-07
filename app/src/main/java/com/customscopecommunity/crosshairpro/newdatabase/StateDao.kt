package com.customscopecommunity.crosshairpro.newdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface StateDao {

    @Insert
    suspend fun addState(state: State)

    @Update
    suspend fun updateState(state: State)

    @Query("SELECT * FROM state_table ")
    suspend fun getAllStates(): State
}