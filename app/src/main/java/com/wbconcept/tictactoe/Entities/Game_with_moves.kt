package com.wbconcept.tictactoe.Entities

import androidx.room.Embedded
import androidx.room.Relation

data class Game_with_moves (
    @Embedded val game: Game,
    @Relation(
        parentColumn = "id",
        entityColumn = "game_id",
         entity = Move::class
    )
    val moves: List<Move>
    )