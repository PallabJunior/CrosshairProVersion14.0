package com.customscopecommunity.crosshairpro.newdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "state_table")
data class State(
    @ColumnInfo(name = "saved_state")
    val isRunning: Boolean = false,

) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}