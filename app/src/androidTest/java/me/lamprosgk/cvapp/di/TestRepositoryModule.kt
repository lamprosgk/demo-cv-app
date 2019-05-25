package me.lamprosgk.cvapp.di

import dagger.Module
import dagger.Provides
import me.lamprosgk.cvapp.data.CvRepository
import me.lamprosgk.cvapp.data.remote.CvService
import org.mockito.Mockito
import javax.inject.Singleton

@Module
class TestRepositoryModule {

    @Provides
    @Singleton
    fun provideCvRepository(service: CvService): CvRepository = Mockito.mock(CvRepository::class.java)

}