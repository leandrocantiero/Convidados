package campagnolo.cantiero.convidados.service.repository

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import campagnolo.cantiero.convidados.service.constants.DataBaseConstants
import campagnolo.cantiero.convidados.service.model.GuestModel

class GuestRepository private constructor(context: Context) {

    private var mGuestDataBaseHelper: GuestDataBaseHelper = GuestDataBaseHelper(context)

    // Singleton
    companion object {
        private lateinit var repository: GuestRepository

        fun getInstance(context: Context): GuestRepository {
            if (!::repository.isInitialized)
                repository = GuestRepository(context)
            return repository
        }
    }

    fun save(guest: GuestModel): Pair<Boolean, String> {
        return try {
            if (guest.id != 0) {
                update(guest)
            } else {
                val guestFromDatabase = getByName(guest.name)
                if (guestFromDatabase != null) {
                    throw GuestAlreadyExistsException()
                }

                insert(guest)
            }
            Pair(true, "")
        } catch (e: Exception) {
            Pair(false, "")
        } catch (e: GuestAlreadyExistsException) {
            Pair(false, "Convidado já adicionado com este nome")
        }
    }

    private fun insert(guest: GuestModel) {
        val db = mGuestDataBaseHelper.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
        contentValues.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, guest.presence)

        db.insert(DataBaseConstants.GUEST.TABLE_NAME, null, contentValues)
    }

    private fun update(guest: GuestModel) {
        val db = mGuestDataBaseHelper.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
        contentValues.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, guest.presence)

        val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
        val args = arrayOf(guest.id.toString())

        db.update(DataBaseConstants.GUEST.TABLE_NAME, contentValues, selection, args)
    }

    fun remove(id: Int): Boolean {
        return try {
            val db = mGuestDataBaseHelper.writableDatabase

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            db.delete(DataBaseConstants.GUEST.TABLE_NAME, selection, args)
            true
        } catch (e: Exception) {
            false
        }
    }

    @SuppressLint("Range")
    fun getById(id: Int): GuestModel? {
        var guest: GuestModel? = null

        return try {
            val db = mGuestDataBaseHelper.readableDatabase

            val args = arrayOf(id.toString())
            val cursor = db.rawQuery("select id, name, presence from guest where id = ?", args)

            if (cursor != null && cursor.count > 0) {
                cursor.moveToFirst()

                guest = GuestModel(
                    id,
                    name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME)),
                    presence = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1
                )
            }

            cursor?.close()

            guest
        } catch (e: Exception) {
            guest
        }
    }

    @SuppressLint("Range")
    fun getByName(name: String): GuestModel? {
        var guest: GuestModel? = null

        return try {
            val db = mGuestDataBaseHelper.readableDatabase

            val args = arrayOf(name)
            val cursor = db.rawQuery("select id, name, presence from guest where name = ?", args)

            if (cursor != null && cursor.count > 0) {
                cursor.moveToFirst()

                guest = GuestModel(
                    id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID)),
                    name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME)),
                    presence = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1
                )
            }

            cursor?.close()

            guest
        } catch (e: Exception) {
            guest
        }
    }

    @SuppressLint("Range")
    fun getAll(): List<GuestModel> {
        val list: MutableList<GuestModel> = ArrayList()

        return try {
            val db = mGuestDataBaseHelper.readableDatabase

            val cursor = db.rawQuery("select id, name, presence from guest", null)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    list.add(
                        GuestModel(
                            id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID)),
                            name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME)),
                            presence = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1
                        )
                    )
                }
            }

            cursor?.close()

            list
        } catch (e: Exception) {
            list
        }
    }

    @SuppressLint("Range")
    fun getPresents(): List<GuestModel> {
        val list: MutableList<GuestModel> = ArrayList()

        return try {
            val db = mGuestDataBaseHelper.readableDatabase

            val cursor = db.rawQuery("select id, name, presence from guest where presence = 1", null)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    list.add(
                        GuestModel(
                            id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID)),
                            name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME)),
                            presence = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1
                        )
                    )
                }
            }

            cursor?.close()

            list
        } catch (e: Exception) {
            list
        }
    }

    @SuppressLint("Range")
    fun getAbsents(): List<GuestModel> {
        val list: MutableList<GuestModel> = ArrayList()

        return try {
            val db = mGuestDataBaseHelper.readableDatabase

            val cursor = db.rawQuery("select id, name, presence from guest where presence = 0", null)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    list.add(
                        GuestModel(
                            id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID)),
                            name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME)),
                            presence = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1
                        )
                    )
                }
            }

            cursor?.close()

            list
        } catch (e: Exception) {
            list
        }
    }
}

class GuestAlreadyExistsException() : Throwable()
