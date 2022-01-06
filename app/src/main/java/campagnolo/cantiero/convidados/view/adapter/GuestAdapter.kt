package campagnolo.cantiero.convidados.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import campagnolo.cantiero.convidados.R
import campagnolo.cantiero.convidados.service.model.GuestModel
import campagnolo.cantiero.convidados.view.listener.GuestListener
import campagnolo.cantiero.convidados.view.viewholder.GuestViewHolder
import kotlinx.coroutines.NonDisposableHandle.parent

class GuestAdapter : RecyclerView.Adapter<GuestViewHolder>() {

    private var mGuestList: List<GuestModel> = arrayListOf()
    private lateinit var mListener: GuestListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.row_guest, parent, false)
        return GuestViewHolder(item, mListener)
    }

    override fun onBindViewHolder(holder: GuestViewHolder, position: Int) {
        holder.bind(mGuestList[position])
    }

    override fun getItemCount(): Int {
        return mGuestList.count()
    }

    fun updateGuests(list: List<GuestModel>) {
        mGuestList = list
        notifyDataSetChanged()
    }

    fun attatchListener(listener: GuestListener) {
        mListener = listener
    }
}