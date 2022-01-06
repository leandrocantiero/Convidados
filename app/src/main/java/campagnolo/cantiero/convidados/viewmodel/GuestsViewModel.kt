package campagnolo.cantiero.convidados.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import campagnolo.cantiero.convidados.service.constants.GuestConstants
import campagnolo.cantiero.convidados.service.model.GuestModel
import campagnolo.cantiero.convidados.service.repository.GuestRepository

class GuestsViewModel(application: Application) : AndroidViewModel(application) {

    private val mGuestRepository = GuestRepository(application.applicationContext)

    private val mGuestList = MutableLiveData<List<GuestModel>>()
    var guestList: LiveData<List<GuestModel>> = mGuestList


    fun load(filter: Int) {
        when (filter) {
            GuestConstants.FILTER.EMPTY -> mGuestList.value = mGuestRepository.getAll()

            GuestConstants.FILTER.PRESENT -> mGuestList.value = mGuestRepository.getPresents()

            GuestConstants.FILTER.ABSENT -> mGuestList.value = mGuestRepository.getAbsents()
        }

    }

    fun remove(id: Int) {
        mGuestRepository.remove(mGuestRepository.getById(id))
    }
}