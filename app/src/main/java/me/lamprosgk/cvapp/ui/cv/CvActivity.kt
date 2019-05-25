package me.lamprosgk.cvapp.ui.cv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import me.lamprosgk.cvapp.CvApplication
import me.lamprosgk.cvapp.R
import me.lamprosgk.cvapp.ui.cv.adapter.RolesAdapter
import me.lamprosgk.cvapp.model.Resume
import me.lamprosgk.cvapp.util.gone
import me.lamprosgk.cvapp.util.visible
import javax.inject.Inject

class CvActivity : AppCompatActivity(), CvContract.View {


    @Inject
    override lateinit var mPresenter: CvContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as CvApplication).appComponent.inject(this)

        setupRecyclerView()
        swipeRefreshContainer.setOnRefreshListener { loadCv() }

        mPresenter.setView(this)

        loadCv()

    }

    private fun setupRecyclerView() {
        experienceRecyclerView.layoutManager = LinearLayoutManager(this)
        experienceRecyclerView.adapter = RolesAdapter()
    }

    private fun loadCv() {
        mPresenter.getCv()
    }

    override fun showCv(cv: Resume) {
        updateUi(cv)
    }

    private fun updateUi(cv: Resume) {
        name.text = getString(R.string.cv_name, cv.firstName, cv.lastName)
        contactDetailsPhone.text = cv.contactDetails.phone
        contactDetailsEmail.text = cv.contactDetails.email
        summary.text = cv.summary

        skills.text = cv.skills.joinToString(separator = ", ")
        (experienceRecyclerView.adapter as RolesAdapter).setItems(cv.workExperience)
    }

    override fun showLoading(loading: Boolean) {
        swipeRefreshContainer.isRefreshing = loading

        if (loading) {
            resumeContainer.gone()
            emptyMessage.text = getString(R.string.message_loading)
            emptyMessage.visible()
        } else {
            emptyMessage.gone()
            resumeContainer.visible()
        }


    }

    override fun showError(error: Throwable) {
        resumeContainer.gone()
        emptyMessage.text = getString(R.string.message_error)
        emptyMessage.visible()
    }
}
