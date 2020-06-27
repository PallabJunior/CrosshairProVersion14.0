package com.customscopecommunity.crosshairpro.screens


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.customscopecommunity.crosshairpro.*
import com.customscopecommunity.crosshairpro.databinding.FragmentMainBinding
import com.customscopecommunity.crosshairpro.services.MainService
import com.customscopecommunity.crosshairpro.services.PremiumService
import kotlinx.android.synthetic.main.permission_dialog.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext


class MainFragment : Fragment(), CoroutineScope {

    private lateinit var broadcastReceiver: BroadcastReceiver

    private lateinit var serviceIntent: Intent
    private lateinit var premiumServiceIntent: Intent
    private lateinit var mBuilder: AlertDialog.Builder

    private lateinit var premimumIntent: Intent
    private lateinit var classicIntent: Intent

    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private lateinit var minimizeButton: Button

    private var checkMinimize = true

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMainBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false
        )

        job = Job()

        serviceIntent = Intent(activity, MainService::class.java)
        premiumServiceIntent = Intent(activity, PremiumService::class.java)

        premimumIntent = Intent(activity, PremiumActivity::class.java)
        classicIntent = Intent(activity, ClassicActivity::class.java)


        broadcastReceiver = object : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {
                binding.buttonStop.visibility = View.GONE
                binding.buttonStart.visibility = View.VISIBLE

                binding.btnMinimize.visibility = View.GONE
            }
        }
        LocalBroadcastManager.getInstance(activity!!)
            .registerReceiver(broadcastReceiver, IntentFilter(action))


        startButton = binding.buttonStart
        stopButton = binding.buttonStop
        minimizeButton = binding.btnMinimize


        when (afterFinishVisibility) {
            1, 2, 3 -> {
                startButton.visibility = View.GONE
                checkMinimize = true
                minimizeButton.visibility = View.VISIBLE

            }
        }

        binding.classicPackage.setOnClickListener {
            if (!Settings.canDrawOverlays(context)) {
                permissionDialog()
            } else {
                startActivity(classicIntent)
            }
        }


        binding.premiumPackage.setOnClickListener {
            if (!Settings.canDrawOverlays(context)) {
                permissionDialog()
            } else {
                startActivity(premimumIntent)
            }
        }


        startButton.setOnClickListener {
            startRequiredService()
        }

        stopButton.setOnClickListener {

            stopServices()
            checkMinimize = false
            minimizeButton.visibility = View.GONE
            stopButton.visibility = View.GONE
            startButton.visibility = View.VISIBLE
        }

        if (checkMinimize) {
            minimizeButton.setOnClickListener {
                activity!!.finish()
            }
        }


        return binding.root
    }

    private fun startRequiredService() {

        if (!Settings.canDrawOverlays(activity)) {

            if (!Settings.canDrawOverlays(context)) {
                permissionDialog()
            }

        } else {


            if (crossNum in 0..50) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    activity!!.startForegroundService(serviceIntent)
                } else {
                    activity!!.startService(serviceIntent)
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    activity!!.startForegroundService(premiumServiceIntent)
                } else {
                    activity!!.startService(premiumServiceIntent)
                }
            }

            startButton.visibility = View.GONE
            stopButton.visibility = View.VISIBLE
            checkMinimize = true
            minimizeButton.visibility = View.VISIBLE
        }
    }

    private fun stopServices() {
        activity!!.stopService(serviceIntent)
        activity!!.stopService(premiumServiceIntent)
    }


    @SuppressLint("InflateParams")
    private fun permissionDialog() {
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.permission_dialog, null)

        mBuilder = AlertDialog.Builder(context)
            .setView(mDialogView)
            .setTitle(getString(R.string.message))

        val mAlertDialog = mBuilder.show()

        mDialogView.permission_btn.setOnClickListener {
            mAlertDialog.dismiss()
            askPermission()
        }
    }

    private fun askPermission() {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse(getString(R.string.package_name))
        )
        startActivityForResult(intent, systemAlertWindowPermission)
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(activity!!).unregisterReceiver(broadcastReceiver)
    }

}