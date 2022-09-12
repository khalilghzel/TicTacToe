package com.wbconcept.tictactoe.Repositories

 
import com.wbconcept.tictactoe.DAO.GameDao
import com.wbconcept.tictactoe.Entities.Game
import com.wbconcept.tictactoe.Entities.Move
import javax.inject.Inject


class GameRepository@Inject constructor(private val gameDao: GameDao) {
    fun insert(game: Game) =
        gameDao.upsert(game)
    fun update(game: Game) =
        gameDao.update(game)

    fun delete(game: Game) =
        gameDao.delete(game)

    fun get_all_games() =
        gameDao.get_all_games()

    fun get_current_game() =
        gameDao.get_current_game()


    fun get_results() =
        gameDao.get_results()


    fun delete_moves() =
        gameDao.delete_moves()

    fun delete_games() =
        gameDao.delete_games()






    fun insert(game: Move) =
        gameDao.upsert(game)
    fun update(game: Move) =
        gameDao.update(game)

    fun delete(game: Move) =
        gameDao.delete(game)

    fun get_all_moves() =
        gameDao.get_all_moves()

}