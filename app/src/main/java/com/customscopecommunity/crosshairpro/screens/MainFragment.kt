package com.customscopecommunity.crosshairpro.screens


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.airbnb.lottie.LottieAnimationView
import com.customscopecommunity.crosshairpro.*
import com.customscopecommunity.crosshairpro.constants.Constants
import com.customscopecommunity.crosshairpro.constants.Constants.CROSSHAIR_BG
import com.customscopecommunity.crosshairpro.constants.Constants.CROSSHAIR_COLOUR
import com.customscopecommunity.crosshairpro.constants.Constants.CROSSHAIR_NUMBER
import com.customscopecommunity.crosshairpro.databinding.FragmentMainBinding
import com.customscopecommunity.crosshairpro.newdatabase.State
import com.customscopecommunity.crosshairpro.newdatabase.StateDatabase
import com.customscopecommunity.crosshairpro.services.MainService
import com.customscopecommunity.crosshairpro.services.PremiumService
import com.customscopecommunity.crosshairpro.viewmodelfactories.MainViewModelFactory
import com.customscopecommunity.crosshairpro.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.permission_dialog.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class MainFragment : Fragment(), CoroutineScope {

    private var isStartBtnClicked = false

    private var savedColor = 0
    private var savedBgLightState = false

    private lateinit var broadcastReceiver: BroadcastReceiver

    private lateinit var serviceIntent: Intent
    private lateinit var premiumServiceIntent: Intent
    private lateinit var mBuilder: AlertDialog.Builder

    private lateinit var premimumIntent: Intent
    private lateinit var classicIntent: Intent

    private lateinit var lottieAnimationView: LottieAnimationView
    private lateinit var startButton: ImageView
    private lateinit var stopButton: ImageView
    private lateinit var minimizeButton: ImageView

    private lateinit var mainViewModel: MainViewModel

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Main

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentMainBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false
        )

        job = Job()

        val vmFactory = MainViewModelFactory(requireActivity().application)
        mainViewModel = ViewModelProvider(this, vmFactory).get(MainViewModel::class.java)

        binding.lifecycleOwner = this

        observeSavedDataFromVM()


        serviceIntent = Intent(activity, MainService::class.java)
        premiumServiceIntent = Intent(activity, PremiumService::class.java)

        premimumIntent = Intent(activity, PremiumActivity::class.java)
        classicIntent = Intent(activity, ClassicActivity::class.java)

        broadcastReceiver = object : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {
                binding.buttonStop.visibility = View.VISIBLE
                binding.buttonStart.visibility = View.GONE
                binding.animationView.visibility = View.INVISIBLE
                binding.btnMinimize.visibility = View.VISIBLE
            }
        }
        LocalBroadcastManager.getInstance(requireActivity())
            .registerReceiver(broadcastReceiver, IntentFilter(Constants.ACTION))


        startButton = binding.buttonStart
        stopButton = binding.buttonStop
        minimizeButton = binding.btnMinimize
        lottieAnimationView = binding.animationView

        binding.classicPackage.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context)) {
                permissionDialog()
            } else {
                classicIntent.apply {
                    putExtra(CROSSHAIR_COLOUR, savedColor)
                    putExtra(CROSSHAIR_BG, savedBgLightState)
                }
                startActivity(classicIntent)
            }
        }


        binding.premiumPackage.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context)) {
                permissionDialog()
            } else {
                startActivity(premimumIntent)
            }
        }


        startButton.setOnClickListener {
            isStartBtnClicked = true
            if (crossNum == 500)
                crossNum = 200

            startRequiredService()
        }

        stopButton.setOnClickListener {

            stopServices()
            minimizeButton.visibility = View.GONE
            stopButton.visibility = View.GONE
            startButton.visibility = View.VISIBLE
            lottieAnimationView.visibility = View.VISIBLE
            saveRunningState()
        }


        minimizeButton.setOnClickListener {
            requireActivity().finish()
        }

        return binding.root
    }

    private fun observeSavedDataFromVM() {
        var isDataObservedOnce = false
        mainViewModel.readSavedCrosshair.observe(viewLifecycleOwner, {
            // to receive the data only once
            if (!isDataObservedOnce) {
                crossNum = it
                isDataObservedOnce = true
            }
        })

        mainViewModel.readSavedColour.observe(viewLifecycleOwner, {
            savedColor = it
        })
        mainViewModel.readSavedBgState.observe(viewLifecycleOwner, {
            savedBgLightState = it
        })
    }

    fun startRequiredService() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(activity)) {
            permissionDialog()
        } else {

            if (isStartBtnClicked) {
                isStartBtnClicked = false
                stopServices()
            }

            if (firstOpen) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                    val toast = Toast.makeText(
                        activity,
                        requireActivity().getString(R.string.please_wait),
                        Toast.LENGTH_SHORT
                    )
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                }
                firstOpen = false
            }

            // saving the current crosshair
            mainViewModel.saveCurrentCrosshair(crossNum)

            if (crossNum in 0..50) {

                serviceIntent.apply {
                    putExtra(CROSSHAIR_NUMBER, crossNum)
                    putExtra(CROSSHAIR_BG, savedBgLightState)
                    putExtra(CROSSHAIR_COLOUR, savedColor)
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    requireActivity().startForegroundService(serviceIntent)
                } else {
                    requireActivity().startService(serviceIntent)
                }
            } else {

                premiumServiceIntent.putExtra(CROSSHAIR_NUMBER, crossNum)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    requireActivity().startForegroundService(premiumServiceIntent)
                } else {
                    requireActivity().startService(premiumServiceIntent)
                }
            }

            lottieAnimationView.visibility = View.INVISIBLE
            startButton.visibility = View.GONE
            stopButton.visibility = View.VISIBLE
            minimizeButton.visibility = View.VISIBLE
        }
    }

    private fun stopServices() {
        requireActivity().stopService(serviceIntent)
        requireActivity().stopService(premiumServiceIntent)
    }


    @SuppressLint("InflateParams")
    private fun permissionDialog() {
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.permission_dialog, null)

        mBuilder = AlertDialog.Builder(context)
            .setView(mDialogView)

        val mAlertDialog = mBuilder.show()
        mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        mDialogView.permission_btn.setOnClickListener {
            mAlertDialog.dismiss()
            askPermission()
        }
    }


    @SuppressLint("InlinedApi")
    private fun askPermission() {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse(getString(R.string.package_name))
        )
        startActivityForResult(intent, systemAlertWindowPermission)
    }


    private fun saveRunningState() {
        CoroutineScope(Main).launch {
            val refState: State? = StateDatabase(requireActivity()).getStateDao().getAllStates()
            val mState = State(false)

            if (refState == null) {
                StateDatabase(requireActivity()).getStateDao().addState(mState)
            } else {
                mState.id = refState.id
                StateDatabase(requireActivity()).getStateDao().updateState(mState)
            }
        }
    }


    fun setButtonsVisibility() {
        startButton.visibility = View.GONE
        lottieAnimationView.visibility = View.INVISIBLE
        minimizeButton.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(requireActivity()).unregisterReceiver(broadcastReceiver)
    }

}