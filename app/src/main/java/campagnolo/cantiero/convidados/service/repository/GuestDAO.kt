package campagnolo.cantiero.convidados.service.repository

import androidx.room.*
import campagnolo.cantiero.convidados.service.model.GuestModel

@Dao
interface GuestDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(guest: GuestModel) : Long

    @Delete
    fun remove(guest: GuestModel) : Int

    @Query("select * from guest where id = :id")
    fun getById(id: Int) : GuestModel

    @Query("select * from guest where name = :name")
    fun getByName(name: String) : GuestModel

    @Query("select * from guest")
    fun getAll() : List<GuestModel>

    @Query("select * from guest where presence = 1")
    fun getPresents() : List<GuestModel>

    @Query("select * from guest where presence = 0")
    fun getAbsents() : List<GuestModel>
}