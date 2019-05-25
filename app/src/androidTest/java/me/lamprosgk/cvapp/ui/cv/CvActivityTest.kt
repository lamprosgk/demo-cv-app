package me.lamprosgk.cvapp.ui.cv

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import io.reactivex.Single
import me.lamprosgk.cvapp.*
import me.lamprosgk.cvapp.data.CvRepository
import me.lamprosgk.cvapp.di.DaggerTestComponent
import me.lamprosgk.cvapp.di.PresenterModule
import me.lamprosgk.cvapp.di.TestRepositoryModule
import me.lamprosgk.cvapp.model.Resume
import me.lamprosgk.cvapp.util.withRecyclerView
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import org.mockito.Mockito.`when` as whenever

@RunWith(AndroidJUnit4::class)
class CvActivityTest : BaseTest() {

    @get:Rule
    val activityRule = ActivityTestRule(CvActivity::class.java,true,false)

    private val app: CvApplication = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
            as CvApplication

    private val successObservable = Single.just(successResponse)
    private val errorObservable: Single<Resume> = Single.error(Throwable())

    @Inject
    lateinit var cvRepository: CvRepository


    @Before
    fun setUp() {

        val testComponent = DaggerTestComponent.builder()
            .testRepositoryModule(TestRepositoryModule())
            .presenterModule(PresenterModule())
            .build()

        testComponent.inject(this)
        app.appComponent = testComponent
    }

    @Test
    fun testMessageVisibleOnScreenWhenLoading() {

        setMockRepositoryData(successObservable)
        activityRule.launchActivity(Intent())
        onView(withId(R.id.emptyMessage)).check(matches(isDisplayed()))
        onView(withId(R.id.emptyMessage)).check(matches(withText(R.string.message_loading)))
    }

    @Test
    fun testMessageNotVisibleOnScreenOnResult() {

        setMockRepositoryData(successObservable)
        launchActivityAndWaitForResponse()
        onView(withId(R.id.emptyMessage)).check(matches(not(isDisplayed())))
    }


    @Test
    fun testCorrectViewsAreVisibleOnSearchResult() {

        setMockRepositoryData(successObservable)
        launchActivityAndWaitForResponse()

        onView(withId(R.id.label_name)).check(matches(isDisplayed()))
        onView(withId(R.id.name)).check(matches(isDisplayed()))
        onView(withId(R.id.label_contact_details)).check(matches(isDisplayed()))
        onView(withId(R.id.contactDetailsPhone)).check(matches(isDisplayed()))
        onView(withId(R.id.contactDetailsEmail)).check(matches(isDisplayed()))
        onView(withId(R.id.label_summary)).check(matches(isDisplayed()))
        onView(withId(R.id.summary)).check(matches(isDisplayed()))
        onView(withId(R.id.label_experience)).check(matches(isDisplayed()))
        onView(withId(R.id.experienceRecyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.label_skills)).check(matches(isDisplayed()))
        onView(withId(R.id.skills)).check(matches(isDisplayed()))

    }

    @Test
    fun testViewsDisplayCorrectResults() {

        setMockRepositoryData(successObservable)
        launchActivityAndWaitForResponse()

        onView(withId(R.id.name)).check(matches(withText("Mock Tester")))
        onView(withId(R.id.contactDetailsPhone)).check(matches(withText("012345678910")))
        onView(withId(R.id.contactDetailsEmail)).check(matches(withText("hi@johndoe.com")))
        onView(withId(R.id.summary)).check(matches(
            withText("Android Developer with over 6 years’ experience in native development, building apps loved by millions. Agile enthusiast, passionate about the Android platform, UX and clean code.")))

        onView(withRecyclerView(R.id.experienceRecyclerView).atPosition(0))
            .check(matches(hasDescendant(withText("KonanCake, London"))))
        onView(withRecyclerView(R.id.experienceRecyclerView).atPosition(0))
            .check(matches(hasDescendant(withText("Software Engineer (Android)"))))
        onView(withRecyclerView(R.id.experienceRecyclerView).atPosition(0))
            .check(matches(hasDescendant(withText("August 2015 - April 2019"))))

        onView(withId(R.id.skills)).check(matches(withText("Kotlin, Java, Android SDK, Testing, git")))
    }

    @Test
    fun testMessageShownOnError() {
        setMockRepositoryData(errorObservable)
        launchActivityAndWaitForResponse()

        // verify views not visible
        onView(withId(R.id.label_name)).check(matches(not(isDisplayed())))
        onView(withId(R.id.name)).check(matches(not(isDisplayed())))
        onView(withId(R.id.label_contact_details)).check(matches(not(isDisplayed())))
        onView(withId(R.id.contactDetailsPhone)).check(matches(not(isDisplayed())))
        onView(withId(R.id.contactDetailsEmail)).check(matches(not(isDisplayed())))
        onView(withId(R.id.label_summary)).check(matches(not(isDisplayed())))
        onView(withId(R.id.summary)).check(matches(not(isDisplayed())))
        onView(withId(R.id.label_experience)).check(matches(not(isDisplayed())))
        onView(withId(R.id.experienceRecyclerView)).check(matches(not(isDisplayed())))
        onView(withId(R.id.label_skills)).check(matches(not(isDisplayed())))
        onView(withId(R.id.skills)).check(matches(not(isDisplayed())))

        // error message visible
        onView(withId(R.id.emptyMessage)).check(matches(isDisplayed()))
        onView(withId(R.id.emptyMessage)).check(matches(withText(R.string.message_error)))


    }



    private fun setMockRepositoryData(responseObservable: Single<Resume>) {
        // set response data and mimic network delay
        whenever(cvRepository.getCv(anyString(), anyString(), anyString()))
            .thenReturn(responseObservable.delay(NETWORK_DELAY_MS, TimeUnit.MILLISECONDS))

    }


    private fun launchActivityAndWaitForResponse() {
        activityRule.launchActivity(Intent())
        Thread.sleep(WAIT_FOR_NETWORK_CALL)
    }



}