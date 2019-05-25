package me.lamprosgk.cvapp.data

import io.reactivex.Single
import io.reactivex.observers.TestObserver
import me.lamprosgk.cvapp.data.remote.CvService
import me.lamprosgk.cvapp.errorObservable
import me.lamprosgk.cvapp.firstName
import me.lamprosgk.cvapp.model.Resume
import me.lamprosgk.cvapp.resume
import me.lamprosgk.cvapp.skills
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import org.mockito.Mockito.`when` as whenever


class CvRepositoryImplTest {

    @Mock
    lateinit var cvService: CvService
    private var cvRepository: CvRepository? = null

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        cvRepository = CvRepositoryImpl(cvService)
    }

    @Test
    fun testGetCvSuccess_InvokesCorrectMethods() {

        // given
        whenever(cvService.getCv(anyString(), anyString(), anyString())).thenReturn(Single.just(resume))

        // when - subscribe to test observable
        val observer = TestObserver<Resume>()
        cvRepository!!.getCv("lampros", "id", "myCv.json").subscribe(observer)

        // then
        observer.awaitTerminalEvent()
        observer.assertNoErrors()

        // verify emitted result
        val onNextEmission = observer.values()[0]
        assert(onNextEmission.firstName == firstName)
        assert(onNextEmission.skills ==  skills)

        verify(cvService).getCv("lampros", "id", "myCv.json")

    }

    @Test
    fun testGetCvFailure_TerminatesWithError() {

        whenever(cvService.getCv(anyString(), anyString(), anyString())).thenReturn(errorObservable)

        val observer = TestObserver<Resume>()
        cvRepository!!.getCv("lampros", "id", "myCv.json").subscribe(observer)

        observer.awaitTerminalEvent()
        observer.assertError(HttpException::class.java)

        verify(cvService).getCv("lampros", "id", "myCv.json")

    }


}