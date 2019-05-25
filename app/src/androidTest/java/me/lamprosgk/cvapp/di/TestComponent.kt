package me.lamprosgk.cvapp.di

import dagger.Component
import me.lamprosgk.cvapp.ui.cv.CvActivityTest
import javax.inject.Singleton


@Singleton
@Component(modules = [NetworkModule::class, TestRepositoryModule::class, PresenterModule::class])
interface TestComponent : AppComponent {

    fun inject(target: CvActivityTest)

}
