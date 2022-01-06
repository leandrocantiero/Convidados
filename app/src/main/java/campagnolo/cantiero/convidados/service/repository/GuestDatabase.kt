package campagnolo.cantiero.convidados.service.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import campagnolo.cantiero.convidados.service.model.GuestModel

@Database(entities = [GuestModel::class], version = 1)
abstract class GuestDatabase : RoomDatabase() {

    abstract fun guestDAO(): GuestDAO

    companion object {
        private lateinit var mInstance: GuestDatabase

        fun getDatabase(context: Context): GuestDatabase {
            if (!::mInstance.isInitialized) {
                synchronized(GuestDatabase::class) {
                    mInstance =
                        Room.databaseBuilder(context, GuestDatabase::class.java, "guestdb")
                            .allowMainThreadQueries()
                            .build()
                }
            }

            return mInstance
        }
    }
}