package com.customscopecommunity.crosshairpro.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import com.customscopecommunity.crosshairpro.R
import com.customscopecommunity.crosshairpro.services.MainService
import com.customscopecommunity.crosshairpro.services.ProService
import com.customscopecommunity.crosshairpro.crossNum
import com.customscopecommunity.crosshairpro.databinding.FragmentProBinding

private const val pMsg: String = "Tap Start"

class ProFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_pro, container, false
        )

        val cService = Intent(activity, MainService::class.java)
        val pService = Intent(activity, ProService::class.java)

        binding.pro1.setOnClickListener {
            crossNum = 1
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.pro2.setOnClickListener {
            crossNum = 2
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.pro3.setOnClickListener {
            crossNum = 3
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.pro4.setOnClickListener {
            crossNum = 4
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.pro5.setOnClickListener {
            crossNum = 5
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.pro6.setOnClickListener {
            crossNum = 6
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.pro7.setOnClickListener {
            crossNum = 7
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.pro8.setOnClickListener {
            crossNum = 8
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.pro9.setOnClickListener {
            crossNum = 9
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.pro10.setOnClickListener {
            crossNum = 10
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.pro11.setOnClickListener {
            crossNum = 11
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.pro12.setOnClickListener {
            crossNum = 12
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.pro13.setOnClickListener {
            crossNum = 13
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.pro14.setOnClickListener {
            crossNum = 14
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.pro15.setOnClickListener {
            crossNum = 15
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.pro16.setOnClickListener {
            crossNum = 16
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.pro17.setOnClickListener {
            crossNum = 17
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.pro18.setOnClickListener {
            crossNum = 18
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.pro19.setOnClickListener {
            crossNum = 19
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.pro20.setOnClickListener {
            crossNum = 20
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }

        return binding.root
    }

    private fun toastShow() {

        LocalBroadcastManager.getInstance(activity!!).sendBroadcast(Intent(action))
        val toast = Toast.makeText(activity, pMsg, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)

        toast.show()

        val handler = Handler()
        handler.postDelayed({ toast.cancel() }, 700)
    }

    private fun closeFg() {
        this.findNavController().navigateUp()
    }


}
