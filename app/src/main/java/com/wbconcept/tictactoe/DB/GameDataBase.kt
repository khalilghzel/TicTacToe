package com.wbconcept.tictactoe.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.wbconcept.tictactoe.DAO.GameDao
import com.wbconcept.tictactoe.Entities.Game
import com.wbconcept.tictactoe.Entities.Move
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(
    entities = [Game::class, Move::class],
    version = 2
)
abstract class GameDataBase : RoomDatabase() {
    abstract fun getGameDao(): GameDao

    companion object {
        @Volatile
        private var instance: GameDataBase? = null
        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {


            instance ?: createDataBase(context).also { instance = it }


        }

        private fun createDataBase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                GameDataBase::class.java,
                "game_db.db"
            ).fallbackToDestructiveMigration()
                .build()


    }

}