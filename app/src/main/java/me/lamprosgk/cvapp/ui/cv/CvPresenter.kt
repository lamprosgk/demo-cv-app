package me.lamprosgk.cvapp.ui.cv

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import me.lamprosgk.cvapp.data.CvRepository
import javax.inject.Inject

class CvPresenter @Inject constructor(private val CvRepository: CvRepository) :
    CvContract.Presenter {

    private var subscription: Disposable? = null
    private var view: CvContract.View? = null

    override fun getCv(username: String, gistId: String, filename: String) {
        checkNotNull(view)

        subscription = CvRepository.getCv(username, gistId, filename)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view!!.showLoading(true) }
            .doOnSuccess { view!!.showLoading(false) }
            .doOnError { view!!.showLoading(false) }
            .retry(3)   // retry 3 times
            .subscribe(
                // onNext
                { view!!.showCv(it) },
                // onError
                {
                    view!!.showError(it)
                })


    }

    override fun setView(view: CvContract.View) {
        this.view = view
    }

    override fun onDestroy() {
        view = null
        subscription?.dispose()
    }


}
