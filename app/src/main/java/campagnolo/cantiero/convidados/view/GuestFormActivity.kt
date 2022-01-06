package campagnolo.cantiero.convidados.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import campagnolo.cantiero.convidados.R
import campagnolo.cantiero.convidados.service.constants.GuestConstants
import campagnolo.cantiero.convidados.viewmodel.GuestFormViewModel
import kotlinx.android.synthetic.main.activity_guest_form.*

class GuestFormActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mViewModel: GuestFormViewModel
    private var mGuestId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_form)

        mViewModel = ViewModelProvider(this).get(GuestFormViewModel::class.java)

        setListeners()
        setObservers()
        loadData()
    }

    private fun loadData() {
        val bundle = intent.extras

        if (bundle != null) {
            mGuestId = bundle.getInt(GuestConstants.GUESTID)
            mViewModel.load(mGuestId)
        } else {
            radio_present.isChecked = true
        }
    }

    private fun setListeners() {
        btn_save.setOnClickListener(this)
        btn_cancel.setOnClickListener(this)
    }

    private fun setObservers() {
        mViewModel.saveGuest.observe(this, Observer {
            if (it.first) {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.sucesso_message),
                    Toast.LENGTH_SHORT
                ).show()

                finish()
            } else {
                if (it.second != "") {
                    Toast.makeText(
                        applicationContext,
                        it.second,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.falha_message),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        })

        mViewModel.loadGuest.observe(this, Observer {
            edit_name.setText(it.name)

            if (it.presence) {
                radio_present.isChecked = true
            } else {
                radio_absent.isChecked = true
            }
        })
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_save) {
            save()
        } else if (view.id == R.id.btn_cancel) {
            finish()
        }
    }

    private fun save() {
        if (validateGuest()) {
            val name = edit_name.text.toString()
            val presence = radio_present.isChecked

            mViewModel.save(mGuestId, name, presence)
        } else {
            Toast.makeText(this, getString(R.string.preencha_todos_os_campos), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun validateGuest(): Boolean {
        return edit_name.text.toString() != "" && (radio_present.isChecked || radio_absent.isChecked)
    }
}