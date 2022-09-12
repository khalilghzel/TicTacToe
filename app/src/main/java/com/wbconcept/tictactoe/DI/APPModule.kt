package com.wbconcept.tictactoe.DI

import android.content.Context
import com.wbconcept.tictactoe.DAO.GameDao
import com.wbconcept.tictactoe.DB.GameDataBase
import com.wbconcept.tictactoe.Repositories.GameRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object  APPModule {

    @Provides
    fun provideGameDao(@ApplicationContext appContext: Context) : GameDao {
        return GameDataBase(appContext).getGameDao()
    }
    @Provides
    fun provideGameRepository(gameDao: GameDao) = GameRepository(gameDao)



}