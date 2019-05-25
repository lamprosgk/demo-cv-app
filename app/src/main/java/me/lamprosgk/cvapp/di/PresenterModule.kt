package me.lamprosgk.cvapp.di

import dagger.Module
import dagger.Provides
import me.lamprosgk.cvapp.ui.cv.CvContract
import me.lamprosgk.cvapp.ui.cv.CvPresenter
import me.lamprosgk.cvapp.data.CvRepository
import javax.inject.Singleton

@Module
class PresenterModule {

    @Provides
    @Singleton
    fun provideCvPresenter(repository: CvRepository): CvContract.Presenter =
        CvPresenter(repository)
}