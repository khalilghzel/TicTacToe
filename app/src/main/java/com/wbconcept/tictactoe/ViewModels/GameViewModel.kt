package com.wbconcept.tictactoe.ViewModels

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.*
import com.wbconcept.tictactoe.Entities.*
import com.wbconcept.tictactoe.Repositories.GameRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class GameViewModel @Inject constructor(
    private val gameRepository: GameRepository
) : ViewModel() {
    var time_rest = MutableLiveData<String>()
    var winner = MutableLiveData<String>()
    var current_game: LiveData<Game_with_moves> = gameRepository.get_current_game()
    var session_result: LiveData<Results> = gameRepository.get_results()
    var player_to_play = MutableLiveData<Int>()
    var game_status: LiveData<Int>
    var timer: CountDownTimer
    val ligne1 = arrayOf("00", "01", "02")
    val ligne2 = arrayOf("10", "11", "12")
    val ligne3 = arrayOf("20", "21", "22")
    val ligne4 = arrayOf("00", "10", "20")
    val ligne5 = arrayOf("01", "11", "21")
    val ligne6 = arrayOf("02", "12", "22")
    val ligne7 = arrayOf("20", "11", "02")
    val ligne8 = arrayOf("00", "11", "22")
    val lines = arrayOf(ligne1, ligne2, ligne3, ligne4, ligne5, ligne6, ligne7, ligne8)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            gameRepository.delete_games()
            gameRepository.delete_moves()
            gameRepository.insert(Game(null, null))
        }

        player_to_play.postValue(1)
        winner.postValue("")
        game_status =
            Transformations.map(current_game) {
                var p1_moves = arrayOf<String>()
                var p2_moves = arrayOf<String>()
                if (current_game.value != null)
                    for (move in current_game.value?.moves!!) {

                        if (move.player == 1) {
                            p1_moves += move.choice
                        } else {
                            p2_moves += move.choice
                        }
                    }

                loop@ for (line in lines) {
                    if (line.all(p1_moves::contains)) {
                        player_to_play.postValue(1)
                        viewModelScope.launch(Dispatchers.IO) {
                            val result = current_game.value?.game
                            result?.winner = 1
                            result?.let { it1 ->
                                gameRepository.update(it1)


                                gameRepository.insert(Game(null, null))

                            }
                        }


                        return@map 1
                    }
                    if (line.all(p2_moves::contains)) {
                        player_to_play.postValue(1)
                        viewModelScope.launch(Dispatchers.IO) {
                            val result = current_game.value?.game
                            result?.winner = 2
                            result?.let { it1 -> gameRepository.update(it1) }

                            gameRepository.insert(Game(null, null))

                        }
                        return@map 2
                    }
                }
                if (current_game.value != null)

                    if (current_game.value!!.moves.size.equals(9)) {
                        player_to_play.postValue(1)

                        viewModelScope.launch(Dispatchers.IO) {


                            gameRepository.insert(Game(null, null))

                        }
                        return@map 3

                    }
                return@map 0

            }


        timer = object : CountDownTimer(1000 * 60 *3, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                val minutes = millisUntilFinished / 1000 / 60
                val seconds = millisUntilFinished / 1000 % 60
                if (seconds < 10)
                    time_rest.postValue("0" + minutes.toString() + ":0" + seconds.toString())
                else
                    time_rest.postValue("0" + minutes.toString() + ":" + seconds.toString())

            }

            override fun onFinish() {
                if (session_result.value != null) {
                    if (session_result.value!!.p1score > session_result.value!!.p2score) {
                        winner.postValue("Player 1 won (" + session_result.value!!.p1score.toString() + "/" + session_result.value!!.p2score + ")")
                    } else if (session_result.value!!.p1score < session_result.value!!.p2score)
                        winner.postValue("Player 2 won (" + session_result.value!!.p1score.toString() + "/" + session_result.value!!.p2score + ")")
                    else
                        winner.postValue("It's a draw (" + session_result.value!!.p1score.toString() + "/" + session_result.value!!.p2score + ")")

                }
                // this.start();
                // player_to_play.postValue(2)
            }
        }
        timer.start()
    }

    fun new_game() {
        viewModelScope.launch(Dispatchers.IO) {
            gameRepository.delete_games()
            gameRepository.delete_moves()
            gameRepository.insert(Game(null, null))
            timer.start()
        }

        player_to_play.postValue(1)
        winner.postValue("")
    }

    fun saveMove(move: String) = viewModelScope.launch(Dispatchers.IO) {
        if (game_status.value == 0 && (winner.value.equals(""))) {
            gameRepository.insert(
                Move(
                    null,
                    current_game.value?.game?.id,
                    player_to_play.value,
                    move
                )
            )
            when (player_to_play.value) {
                1 -> player_to_play.postValue(2)
                2 -> player_to_play.postValue(1)
            }
        }
    }


}