package com.wbconcept.tictactoe.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "games"
)
data class Game(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var winner: Int?,




    )