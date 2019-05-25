package me.lamprosgk.cvapp.ui.cv

import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import me.lamprosgk.cvapp.data.CvRepository
import me.lamprosgk.cvapp.resume
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when` as whenever

class CvPresenterTest {

    @Mock
    lateinit var view: CvContract.View
    @Mock
    lateinit var cvRepository: CvRepository

    private lateinit var cvPresenter: CvContract.Presenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        // replace Schedulers for test
        RxAndroidPlugins.reset()
        RxJavaPlugins.reset()
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        cvPresenter = CvPresenter(cvRepository)
        cvPresenter.setView(view)
    }

    @Test
    fun testGetCvSuccess_InvokesCorrectMethods() {
        whenever(cvRepository.getCv(anyString(), anyString(), anyString())).thenReturn(Single.just(resume))

        cvPresenter.getCv()

        // check that the right methods called in the view and in right order
        verify(view).showLoading(true)
        verify(view).showLoading(false)
        verify(view).showCv(resume)
        verify(view, never()).showError(Throwable())
    }

    @Test
    fun testGetCvFailure_RetriesThreeTimes() {
        val throwable = Throwable()

        whenever(cvRepository.getCv(anyString(), anyString(), anyString())).thenReturn(Single.error(throwable))
        cvPresenter.getCv()

        // verify that method is called 4 times (initial call + 3 retries)
        verify(view, times(4)).showLoading(true)
    }

    @Test
    fun testGetCvFailure_ErrorIsShow() {
        val throwable = Throwable()

        whenever(cvRepository.getCv(anyString(), anyString(), anyString())).thenReturn(Single.error(throwable))
        cvPresenter.getCv()

        verify(view, atLeastOnce()).showLoading(true)
        verify(view, atLeastOnce()).showLoading(false)
        verify(view).showError(throwable)
        verify(view, never()).showCv(me.lamprosgk.cvapp.any())
    }
}