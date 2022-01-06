package campagnolo.cantiero.convidados.service.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "guest")
class GuestModel {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var name: String = ""
    var presence: Boolean = true
}