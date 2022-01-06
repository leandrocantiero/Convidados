package campagnolo.cantiero.convidados.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import campagnolo.cantiero.convidados.service.model.GuestModel
import campagnolo.cantiero.convidados.service.repository.GuestRepository

class GuestFormViewModel(application: Application) : AndroidViewModel(application) {

    private val mContext = application.applicationContext
    private val mGuestRepository: GuestRepository = GuestRepository.getInstance(mContext)

    private var mSaveGuest = MutableLiveData<Pair<Boolean, String>>()
    val saveGuest: LiveData<Pair<Boolean, String>> = mSaveGuest

    private var mGuest = MutableLiveData<GuestModel>()
    val loadGuest: LiveData<GuestModel> = mGuest

    fun save(id: Int, name: String, presence: Boolean) {
        val guest = GuestModel(id, name, presence)
        mSaveGuest.value = mGuestRepository.save(guest)
    }

    fun load(id: Int) {
        mGuest.value = mGuestRepository.getById(id)
    }
}