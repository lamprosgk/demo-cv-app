package me.lamprosgk.cvapp

import android.app.Application
import me.lamprosgk.cvapp.di.AppComponent
import me.lamprosgk.cvapp.di.DaggerAppComponent
import me.lamprosgk.cvapp.di.PresenterModule
import me.lamprosgk.cvapp.di.RepositoryModule

class CvApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = createComponent()
    }

    private fun createComponent(): AppComponent =
        DaggerAppComponent.builder()
            .repositoryModule(RepositoryModule())
            .presenterModule(PresenterModule())
            .build()
}