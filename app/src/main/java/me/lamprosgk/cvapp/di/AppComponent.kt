package me.lamprosgk.cvapp.di

import dagger.Component
import me.lamprosgk.cvapp.ui.cv.CvActivity
import javax.inject.Singleton


@Singleton
@Component(modules = [NetworkModule::class, RepositoryModule::class, PresenterModule::class])
interface AppComponent {

    fun inject(target: CvActivity)
}