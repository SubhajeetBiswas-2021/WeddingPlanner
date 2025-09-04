package com.subhajeet.weddingplanner.di

import android.app.Application
import androidx.room.Room
import com.subhajeet.weddingplanner.Usermodel.UserDao
import com.subhajeet.weddingplanner.Usermodel.UserDatabase
import com.subhajeet.weddingplanner.GuestModel.GuestDao
import com.subhajeet.weddingplanner.GuestModel.GuestDatabase
import com.subhajeet.weddingplanner.model.TaskDao
import com.subhajeet.weddingplanner.model.TaskDatabase
import com.subhajeet.weddingplanner.repo.GuestRepository
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

    @Provides
    @Singleton
    fun provideUserDatabase(context : Application): UserDatabase {             //returning the database
        return Room.databaseBuilder(
            context.baseContext,
            UserDatabase::class.java,
            "user_database"
        ).build()
    }


    @Provides
    @Singleton
    fun provideUserDao(db: UserDatabase): UserDao {
        return db.getUserDao()
    }


    @Provides
    @Singleton
    fun provideGuestDatabase(context: Application): GuestDatabase {
        return Room.databaseBuilder(
            context.baseContext,
            GuestDatabase::class.java,
            "guest_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideGuestDao(db: GuestDatabase): GuestDao {
        return db.guestDao()
    }

    @Provides
    @Singleton
    fun provideGuestRepository(guestDao: GuestDao): GuestRepository {
        return GuestRepository(guestDao)
    }




}