package com.customscopecommunity.crosshairpro.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "positions_table")
data class Position(
    @ColumnInfo(name = "saved_vertical_position")
    val vPosition: Int = 0,
    @ColumnInfo(name = "saved_horizontal_position")
    val hPosition: Int = 0
) {
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}