package com.customscopecommunity.crosshairpro.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PositionDao {

    @Insert
    suspend fun addPosition(position: Position)

    @Update
    suspend fun updatePosition(position: Position)

    @Query("SELECT * FROM positions_table ")
    suspend fun getAllPositions(): Position
}