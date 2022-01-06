package campagnolo.cantiero.convidados.service.repository

import android.content.Context
import campagnolo.cantiero.convidados.service.model.GuestModel

class GuestRepository(context: Context) {

    private val mDatabase = GuestDatabase.getDatabase(context).guestDAO()

    fun save(guest: GuestModel): Boolean {
        return mDatabase.save(guest) > 0
    }

    fun remove(guest: GuestModel): Boolean {
        return mDatabase.remove(guest) > 0
    }

    fun getById(id: Int): GuestModel {
        return mDatabase.getById(id)
    }

    fun getByName(name: String): GuestModel {
        return mDatabase.getByName(name)
    }

    fun getAll(): List<GuestModel> {
        return mDatabase.getAll()
    }

    fun getPresents(): List<GuestModel> {
        return mDatabase.getPresents()
    }

    fun getAbsents(): List<GuestModel> {
        return mDatabase.getAbsents()
    }
}