package com.wbconcept.tictactoe.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import com.wbconcept.tictactoe.Entities.Game
import com.wbconcept.tictactoe.Entities.Game_with_moves
import com.wbconcept.tictactoe.Entities.Move
import com.wbconcept.tictactoe.Entities.Results

@Dao
interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(game: Game): Long

    @Update
    fun update(game: Game)

    @Query("select  *   from games ")
    fun get_all_games(): LiveData<List<Game>>

    @Transaction
    @Query("select  *   from games ORDER BY id DESC LIMIT 1 ")
    fun get_current_game(): LiveData<Game_with_moves>



    @Query("SELECT * FROM (SELECT count(*) as p1score FROM games WHERE winner = 1 ),( SELECT count(*) as p2score FROM games WHERE winner = 2)")
    fun get_results(): LiveData<Results>


    @Query("delete  from games")
    fun delete_games()

    @Query("delete  from moves")
    fun delete_moves()




    @Delete()
    fun delete(game: Game)




    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(move: Move): Long

    @Update
    fun update(move: Move)

    @Query("select  *   from moves ")
    fun get_all_moves(): LiveData<List<Move>>

    @Delete()
    fun delete(move: Move)





}