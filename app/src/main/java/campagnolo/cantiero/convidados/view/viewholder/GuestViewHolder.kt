package campagnolo.cantiero.convidados.view.viewholder

import android.app.AlertDialog
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import campagnolo.cantiero.convidados.R
import campagnolo.cantiero.convidados.service.model.GuestModel
import campagnolo.cantiero.convidados.view.listener.GuestListener

class GuestViewHolder(itemView: View, private val listener: GuestListener) :
    RecyclerView.ViewHolder(itemView) {
    fun bind(guest: GuestModel) {
        val textName = itemView.findViewById<TextView>(R.id.text_name)
        textName.text = guest.name

        textName.setOnClickListener {
            listener.onClick(guest.id)
        }

        textName.setOnLongClickListener {
            AlertDialog.Builder(itemView.context)
                .setTitle(R.string.remocao_convidado)
                .setMessage(R.string.deseja_remover)
                .setPositiveButton("Remover") { dialog, which ->
                    listener.onDelete(guest.id)
                }
                .setNeutralButton("Cancelar", null)
                .show()

            true
        }

        val imagePresence = itemView.findViewById<ImageView>(R.id.image_presence)
        val imageResource = if (guest.presence) R.drawable.ic_check else R.drawable.ic_close
        imagePresence.setImageResource(imageResource)
    }
}