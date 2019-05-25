package me.lamprosgk.cvapp.ui.cv

import me.lamprosgk.cvapp.*
import me.lamprosgk.cvapp.model.Resume

/**
 * MVP contract between presenter and view for Resume screen
 */
interface CvContract {

    interface View : BaseView<Presenter> {
        fun showCv(cv: Resume)
    }

    interface Presenter : BasePresenter<View> {
        /**
         * Default arguments provided, but allows for any json gist which conforms to the same schema to be loaded
         */
        fun getCv(username: String = DEFAULT_GITHUB_USER,
                  gistId: String = DEFAULT_GIST_ID,
                  filename: String = DEFAULT_GIST_FILENAME)
    }
}