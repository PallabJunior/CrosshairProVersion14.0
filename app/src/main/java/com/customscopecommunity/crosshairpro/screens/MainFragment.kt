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
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.customscopecommunity.crosshairpro.*
import com.customscopecommunity.crosshairpro.services.MainService
import com.customscopecommunity.crosshairpro.services.ProService
import com.customscopecommunity.crosshairpro.databinding.FragmentMainBinding
import com.customscopecommunity.crosshairpro.services.PremiumService
import kotlinx.android.synthetic.main.permission_dialog.view.*


class MainFragment : Fragment() {

    private lateinit var broadcastReceiver: BroadcastReceiver

    private lateinit var serviceIntent: Intent
    private lateinit var proServiceIntent: Intent
    private lateinit var premiumServiceIntent: Intent
    private lateinit var mBuilder: AlertDialog.Builder

    private lateinit var premimumIntent: Intent
    private lateinit var proIntent: Intent
    private lateinit var classicIntent: Intent

    private lateinit var startButton: Button
    private lateinit var stopButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMainBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false
        )

        serviceIntent = Intent(activity, MainService::class.java)
        proServiceIntent = Intent(activity, ProService::class.java)
        premiumServiceIntent = Intent(activity, PremiumService::class.java)

        premimumIntent = Intent(activity, PremiumActivity::class.java)
        proIntent = Intent(activity, ProActivity::class.java)
        classicIntent = Intent(activity, ClassicActivity::class.java)


        broadcastReceiver = object : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {
                binding.buttonStop.visibility = View.GONE
                binding.buttonStart.visibility = View.VISIBLE
            }
        }
        LocalBroadcastManager.getInstance(activity!!)
            .registerReceiver(broadcastReceiver, IntentFilter(action))


        startButton = binding.buttonStart
        stopButton = binding.buttonStop



        when (afterFinishVisibility) {
            1 -> startButton.visibility = View.GONE
            2 -> startButton.visibility = View.GONE
            3 -> startButton.visibility = View.GONE
        }

        binding.classicPackage.setOnClickListener {
            if (!Settings.canDrawOverlays(context)) {
                permissionDialog()
            } else {
                startActivity(classicIntent)
            }


        }

        binding.proPackage.setOnClickListener {
            if (!Settings.canDrawOverlays(context)) {
                permissionDialog()
            } else {
                startActivity(proIntent)
            }

        }

        binding.premiumPackage.setOnClickListener {
            if (!Settings.canDrawOverlays(context)) {
                permissionDialog()
            } else {
                startActivity(premimumIntent)
            }
        }


        binding.buttonStart.setOnClickListener {
            startRequiredService()
        }

        binding.buttonStop.setOnClickListener {

            stopServices()

            stopButton.visibility = View.GONE
            startButton.visibility = View.VISIBLE
        }


        return binding.root
    }

    private fun startRequiredService() {

        if (!Settings.canDrawOverlays(activity)) {

            if (!Settings.canDrawOverlays(context)) {
                permissionDialog()
            }

        } else {

            if (crossNum in 0..19) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    activity!!.startForegroundService(proServiceIntent)
                } else {
                    activity!!.startService(proServiceIntent)
                }
            } else {

                if (crossNum in 51..70) {
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


            }
            startButton.visibility = View.GONE
            stopButton.visibility = View.VISIBLE
        }
    }

    private fun stopServices() {
        activity!!.stopService(serviceIntent)
        activity!!.stopService(proServiceIntent)
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
