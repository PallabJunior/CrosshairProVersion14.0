package com.customscopecommunity.crosshairpro.fragments


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.findNavController
import com.customscopecommunity.crosshairpro.R
import com.customscopecommunity.crosshairpro.services.MainService
import com.customscopecommunity.crosshairpro.services.ProService
import com.customscopecommunity.crosshairpro.afterFinishVisibility
import com.customscopecommunity.crosshairpro.crossNum
import com.customscopecommunity.crosshairpro.databinding.FragmentMainBinding


private const val toastMsg: String = "Permission required to run this app."

class MainFragment : Fragment() {

    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentMainBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false
        )

        //for start button visibility issue
        broadcastReceiver = object : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {
                // do your listener event stuff
                binding.buttonStop.visibility = View.GONE
                binding.buttonStart.visibility = View.VISIBLE
            }
        }
        LocalBroadcastManager.getInstance(activity!!)
            .registerReceiver(broadcastReceiver, IntentFilter(action))


        val startButton = binding.buttonStart
        val stopButton = binding.buttonStop


        //button visibility
        when (afterFinishVisibility) {
            1 -> startButton.visibility = View.GONE    //MainService Classic
            2 -> startButton.visibility = View.GONE    //ProService
        }

        //classic and pro package
        binding.classicPackage.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_mainFragment_to_classicFragment)
        }

        binding.proPackage.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_mainFragment_to_proFragment)
        }

        //initializing services
        val serviceIntent = Intent(activity, MainService::class.java)
        val proServiceIntent = Intent(activity, ProService::class.java)

        binding.buttonStart.setOnClickListener {

            if (!Settings.canDrawOverlays(activity)) {
                needPermission()
                activity!!.finish()
            } else {

                if (crossNum in 1..20) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        activity!!.startForegroundService(proServiceIntent)
                    } else {
                        activity!!.startService(proServiceIntent)
                    }
                } else {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        activity!!.startForegroundService(serviceIntent)
                    } else {
                        activity!!.startService(serviceIntent)
                    }
                }
                startButton.visibility = View.GONE
                stopButton.visibility = View.VISIBLE
            }

        }

        binding.buttonStop.setOnClickListener {

            activity!!.stopService(serviceIntent)
            activity!!.stopService(proServiceIntent)

            stopButton.visibility = View.GONE
            startButton.visibility = View.VISIBLE
        }


        return binding.root
    }

    private fun needPermission() {
        Toast.makeText(activity, toastMsg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(activity!!).unregisterReceiver(broadcastReceiver)
    }

}
