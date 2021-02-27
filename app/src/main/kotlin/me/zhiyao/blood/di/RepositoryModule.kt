package me.zhiyao.blood.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.zhiyao.blood.data.db.dao.BloodPressureDao
import me.zhiyao.blood.data.repo.BloodPressureRepository
import javax.inject.Singleton

/**
 *
 * @author WangZhiYao
 * @date 2021/2/25
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideBloodPressureRepository(bloodPressureDao: BloodPressureDao) =
        BloodPressureRepository(bloodPressureDao)

}