package me.lamprosgk.cvapp.di

import dagger.Module
import dagger.Provides
import me.lamprosgk.cvapp.data.CvRepository
import me.lamprosgk.cvapp.data.CvRepositoryImpl
import me.lamprosgk.cvapp.data.remote.CvService
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideCvRepository(service: CvService): CvRepository = CvRepositoryImpl(service)
}