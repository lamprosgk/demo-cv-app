package me.lamprosgk.cvapp


interface BasePresenter<T> {

    fun setView(view: T)
    fun onDestroy()
}