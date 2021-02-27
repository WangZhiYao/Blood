package me.zhiyao.blood.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.zhiyao.blood.data.db.AppDatabase
import javax.inject.Singleton

/**
 *
 * @author WangZhiYao
 * @date 2021/2/25
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DB_NAME = "BloodPressure.db"

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(appContext, AppDatabase::class.java, DB_NAME)
            .build()

    @Provides
    @Singleton
    fun provideBloodPressureDao(appDatabase: AppDatabase) = appDatabase.bloodPressureDao()

}