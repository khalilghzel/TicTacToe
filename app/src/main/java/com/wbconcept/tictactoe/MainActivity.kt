package com.wbconcept.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.wbconcept.tictactoe.ViewModels.GameViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val gameViewModel: GameViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //timer
        gameViewModel.time_rest.observe(this, Observer {
            timer.text = it
        })
        //every round winner
        gameViewModel.game_status.observe(this, Observer { response ->
            when (response) {
                1 -> {
                    Snackbar.make(
                        rootlayout,
                        "Player 1 won this round", Snackbar.LENGTH_SHORT
                    ).show()
                }
                2 -> {
                    Snackbar.make(
                        rootlayout,
                        "Player 2 won this round", Snackbar.LENGTH_SHORT
                    ).show()
                }
                3 -> {
                    Snackbar.make(
                        rootlayout,
                        "Draw !!!", Snackbar.LENGTH_SHORT
                    ).show()
                }

            }

        })
    //score
        gameViewModel.session_result.observe(this, Observer { response ->
            p1_res.text = response.p1score.toString()
            p2_res.text = response.p2score.toString()
        })


        // filling the board with the current game moves
        gameViewModel.current_game.observe(this, Observer { response ->
            val cross = R.drawable.cross
            val check = R.drawable.circle
            clear_board()
            if (response != null)
                for (move in response.moves) {
                    when (move.choice) {
                        "00" -> {
                            if (move.player == 1)
                                iv00.setImageResource(check) else iv00.setImageResource(cross)
                        }
                        "01" -> {
                            if (move.player == 1)
                                iv01.setImageResource(check) else iv01.setImageResource(cross)
                        }
                        "02" -> {
                            if (move.player == 1)
                                iv02.setImageResource(check) else iv02.setImageResource(cross)
                        }
                        "10" -> {
                            if (move.player == 1)
                                iv10.setImageResource(check) else iv10.setImageResource(cross)
                        }
                        "11" -> {
                            if (move.player == 1)
                                iv11.setImageResource(check) else iv11.setImageResource(cross)
                        }
                        "12" -> {
                            if (move.player == 1)
                                iv12.setImageResource(check) else iv12.setImageResource(cross)
                        }
                        "20" -> {
                            if (move.player == 1)
                                iv20.setImageResource(check) else iv20.setImageResource(cross)
                        }
                        "21" -> {
                            if (move.player == 1)
                                iv21.setImageResource(check) else iv21.setImageResource(cross)
                        }
                        "22" -> {
                            if (move.player == 1)
                                iv22.setImageResource(check) else iv22.setImageResource(cross)
                        }
                    }

                }

        })
        //arrow for   player1 or player2
        gameViewModel.player_to_play.observe(this, Observer {
            when (it) {
                1 -> {
                    p1_arrow.visibility = View.VISIBLE
                    p2_arrow.visibility = View.INVISIBLE
                }
                2 -> {
                    p1_arrow.visibility = View.INVISIBLE
                    p2_arrow.visibility = View.VISIBLE
                }
            }
        })

        //game result
        gameViewModel.winner.observe(this, Observer {

            if (!it.equals(""))
                Snackbar.make(rootlayout, it, BaseTransientBottomBar.LENGTH_INDEFINITE
                )
                    .setAction("New game", View.OnClickListener {
                        gameViewModel.new_game()
                    })
                    .show()
         })
    }

    //saving a move
    fun image_clicked(v: View) {
        if (v is ImageView) {
            val image = v as ImageView
            if (image.getDrawable() == null)
                gameViewModel.saveMove(v.transitionName)
        }
    }

    fun clear_board() {
        iv00.setImageResource(0)
        iv01.setImageResource(0)
        iv02.setImageResource(0)
        iv10.setImageResource(0)
        iv11.setImageResource(0)
        iv12.setImageResource(0)
        iv20.setImageResource(0)
        iv21.setImageResource(0)
        iv22.setImageResource(0)
    }


}