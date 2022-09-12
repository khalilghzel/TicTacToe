package com.wbconcept.tictactoe.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "moves"
)
data class Move(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val game_id: Int?,
    val player: Int?,
    val choice: String
)