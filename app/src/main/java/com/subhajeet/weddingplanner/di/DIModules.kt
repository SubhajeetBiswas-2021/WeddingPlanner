package com.subhajeet.weddingplanner.di

import android.app.Application
import androidx.room.Room
import com.subhajeet.weddingplanner.model.TaskDao
import com.subhajeet.weddingplanner.model.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DIModules {

    @Provides
    @Singleton
    fun provideDatabase(context : Application): TaskDatabase {             //returning the database
        return Room.databaseBuilder(
            context.baseContext,
            TaskDatabase::class.java,
            "task_database"
        ).build()
    }


    @Provides
    @Singleton
    fun provideTaskDao(db: TaskDatabase): TaskDao {
        return db.getDao()
    }
}