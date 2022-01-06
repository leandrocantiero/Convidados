package campagnolo.cantiero.convidados.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import campagnolo.cantiero.convidados.R
import campagnolo.cantiero.convidados.service.constants.GuestConstants
import campagnolo.cantiero.convidados.view.adapter.GuestAdapter
import campagnolo.cantiero.convidados.view.listener.GuestListener
import campagnolo.cantiero.convidados.viewmodel.GuestsViewModel

class AllGuestsFragment : Fragment() {

    private lateinit var mViewModel: GuestsViewModel
    private val mAdapter: GuestAdapter = GuestAdapter()
    private lateinit var mListener: GuestListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewModel = ViewModelProvider(this).get(GuestsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_all, container, false)

        val recycler = root.findViewById<RecyclerView>(R.id.recycler_all_guests)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = mAdapter

        setObservers()
        setListeners()
        mViewModel.load(GuestConstants.FILTER.EMPTY)

        return root
    }

    private fun setObservers() {
        mViewModel.guestList.observe(viewLifecycleOwner, Observer {
            mAdapter.updateGuests(it)
        })
    }

    private fun setListeners() {
        mListener = object : GuestListener {
            override fun onClick(id: Int) {
                val intent = Intent(context, GuestFormActivity::class.java)
                val bundle = Bundle()

                bundle.putInt(GuestConstants.GUESTID, id)
                intent.putExtras(bundle)

                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                mViewModel.remove(id)
                mViewModel.load(GuestConstants.FILTER.EMPTY)
            }
        }

        mAdapter.attatchListener(mListener)
    }

    override fun onResume() {
        super.onResume()
        mViewModel.load(GuestConstants.FILTER.EMPTY)
    }
}