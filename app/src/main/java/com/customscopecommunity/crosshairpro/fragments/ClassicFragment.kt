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
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.DataBindingUtil
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import com.customscopecommunity.crosshairpro.R
import com.customscopecommunity.crosshairpro.services.MainService
import com.customscopecommunity.crosshairpro.services.ProService
import com.customscopecommunity.crosshairpro.crossNum
import com.customscopecommunity.crosshairpro.databinding.FragmentClassicBinding


var colour: Int = 0
var backgroundLight: Int = 0
const val action: String = "activity-2-initialized"
private const val msg: String = "Tap Start"

class ClassicFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentClassicBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_classic, container, false
        )
        if (backgroundLight == 1) {
            binding.lightSwitch.isChecked = true

            binding.classic1.setBackgroundResource(R.drawable.c_background)
            binding.classic2.setBackgroundResource(R.drawable.c_background)
            binding.classic3.setBackgroundResource(R.drawable.c_background)
            binding.classic4.setBackgroundResource(R.drawable.c_background)
            binding.classic5.setBackgroundResource(R.drawable.c_background)
            binding.classic6.setBackgroundResource(R.drawable.c_background)
            binding.classic7.setBackgroundResource(R.drawable.c_background)
            binding.classic8.setBackgroundResource(R.drawable.c_background)
            binding.classic9.setBackgroundResource(R.drawable.c_background)
            binding.classic10.setBackgroundResource(R.drawable.c_background)
            binding.classic11.setBackgroundResource(R.drawable.c_background)
            binding.classic12.setBackgroundResource(R.drawable.c_background)
            binding.classic13.setBackgroundResource(R.drawable.c_background)
            binding.classic14.setBackgroundResource(R.drawable.c_background)
            binding.classic15.setBackgroundResource(R.drawable.c_background)
            binding.classic16.setBackgroundResource(R.drawable.c_background)
            binding.classic17.setBackgroundResource(R.drawable.c_background)
            binding.classic18.setBackgroundResource(R.drawable.c_background)
            binding.classic19.setBackgroundResource(R.drawable.c_background)
            binding.classic20.setBackgroundResource(R.drawable.c_background)

        }

        val cService = Intent(activity, MainService::class.java)
        val pService = Intent(activity, ProService::class.java)


        binding.lightSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                backgroundLight = 1

                binding.classic1.setBackgroundResource(R.drawable.c_background)
                binding.classic2.setBackgroundResource(R.drawable.c_background)
                binding.classic3.setBackgroundResource(R.drawable.c_background)
                binding.classic4.setBackgroundResource(R.drawable.c_background)
                binding.classic5.setBackgroundResource(R.drawable.c_background)
                binding.classic6.setBackgroundResource(R.drawable.c_background)
                binding.classic7.setBackgroundResource(R.drawable.c_background)
                binding.classic8.setBackgroundResource(R.drawable.c_background)
                binding.classic9.setBackgroundResource(R.drawable.c_background)
                binding.classic10.setBackgroundResource(R.drawable.c_background)
                binding.classic11.setBackgroundResource(R.drawable.c_background)
                binding.classic12.setBackgroundResource(R.drawable.c_background)
                binding.classic13.setBackgroundResource(R.drawable.c_background)
                binding.classic14.setBackgroundResource(R.drawable.c_background)
                binding.classic15.setBackgroundResource(R.drawable.c_background)
                binding.classic16.setBackgroundResource(R.drawable.c_background)
                binding.classic17.setBackgroundResource(R.drawable.c_background)
                binding.classic18.setBackgroundResource(R.drawable.c_background)
                binding.classic19.setBackgroundResource(R.drawable.c_background)
                binding.classic20.setBackgroundResource(R.drawable.c_background)
            } else {
                backgroundLight = 2

                binding.classic1.setBackgroundResource(0)
                binding.classic2.setBackgroundResource(0)
                binding.classic3.setBackgroundResource(0)
                binding.classic4.setBackgroundResource(0)
                binding.classic5.setBackgroundResource(0)
                binding.classic6.setBackgroundResource(0)
                binding.classic7.setBackgroundResource(0)
                binding.classic8.setBackgroundResource(0)
                binding.classic9.setBackgroundResource(0)
                binding.classic10.setBackgroundResource(0)
                binding.classic11.setBackgroundResource(0)
                binding.classic12.setBackgroundResource(0)
                binding.classic13.setBackgroundResource(0)
                binding.classic14.setBackgroundResource(0)
                binding.classic15.setBackgroundResource(0)
                binding.classic16.setBackgroundResource(0)
                binding.classic17.setBackgroundResource(0)
                binding.classic18.setBackgroundResource(0)
                binding.classic19.setBackgroundResource(0)
                binding.classic20.setBackgroundResource(0)
            }
        }

        binding.changeRed.setOnClickListener {

            DrawableCompat.setTint(
                binding.classic1.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                binding.classic2.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                binding.classic3.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                binding.classic4.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                binding.classic5.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                binding.classic6.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                binding.classic7.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                binding.classic8.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                binding.classic9.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                binding.classic10.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                binding.classic11.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                binding.classic12.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                binding.classic13.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                binding.classic14.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                binding.classic15.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                binding.classic16.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                binding.classic17.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                binding.classic18.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                binding.classic19.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                binding.classic20.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.primary
                )
            )

            colour = 1
        }
        binding.changeWhite.setOnClickListener {

            DrawableCompat.setTint(
                binding.classic1.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                binding.classic2.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                binding.classic3.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                binding.classic4.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                binding.classic5.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                binding.classic6.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                binding.classic7.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                binding.classic8.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                binding.classic9.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                binding.classic10.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                binding.classic11.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                binding.classic12.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                binding.classic13.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                binding.classic14.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                binding.classic15.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                binding.classic16.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                binding.classic17.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                binding.classic18.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                binding.classic19.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                binding.classic20.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.white
                )
            )

            colour = 2
        }
        binding.changeGreen.setOnClickListener {

            DrawableCompat.setTint(
                binding.classic1.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                binding.classic2.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                binding.classic3.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                binding.classic4.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                binding.classic5.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                binding.classic6.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                binding.classic7.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                binding.classic8.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                binding.classic9.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                binding.classic10.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                binding.classic11.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                binding.classic12.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                binding.classic13.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                binding.classic14.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                binding.classic15.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                binding.classic16.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                binding.classic17.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                binding.classic18.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                binding.classic19.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                binding.classic20.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.green
                )
            )

            colour = 3
        }
        binding.changeYellow.setOnClickListener {

            DrawableCompat.setTint(
                binding.classic1.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                binding.classic2.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                binding.classic3.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                binding.classic4.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                binding.classic5.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                binding.classic6.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                binding.classic7.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                binding.classic8.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                binding.classic9.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                binding.classic10.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                binding.classic11.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                binding.classic12.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                binding.classic13.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                binding.classic14.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                binding.classic15.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                binding.classic16.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                binding.classic17.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                binding.classic18.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                binding.classic19.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                binding.classic20.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.yellow
                )
            )

            colour = 4
        }
        binding.changePurple.setOnClickListener {

            DrawableCompat.setTint(
                binding.classic1.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                binding.classic2.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                binding.classic3.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                binding.classic4.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                binding.classic5.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                binding.classic6.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                binding.classic7.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                binding.classic8.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                binding.classic9.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                binding.classic10.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                binding.classic11.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                binding.classic12.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                binding.classic13.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                binding.classic14.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                binding.classic15.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                binding.classic16.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                binding.classic17.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                binding.classic18.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                binding.classic19.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                binding.classic20.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.purple
                )
            )

            colour = 5
        }
        binding.changeBlue.setOnClickListener {

            DrawableCompat.setTint(
                binding.classic1.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                binding.classic2.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                binding.classic3.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                binding.classic4.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                binding.classic5.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                binding.classic6.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                binding.classic7.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                binding.classic8.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                binding.classic9.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                binding.classic10.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                binding.classic11.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                binding.classic12.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                binding.classic13.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                binding.classic14.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                binding.classic15.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                binding.classic16.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                binding.classic17.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                binding.classic18.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                binding.classic19.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                binding.classic20.drawable, ContextCompat.getColor(
                    activity!!,
                    R.color.blue
                )
            )

            colour = 6
        }
        binding.classic1.setOnClickListener {
            crossNum = 51
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.classic2.setOnClickListener {
            crossNum = 52
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.classic3.setOnClickListener {
            crossNum = 53
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.classic4.setOnClickListener {
            crossNum = 54
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.classic5.setOnClickListener {
            crossNum = 55
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.classic6.setOnClickListener {
            crossNum = 56
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.classic7.setOnClickListener {
            crossNum = 57
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.classic8.setOnClickListener {
            crossNum = 58
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.classic9.setOnClickListener {
            crossNum = 59
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.classic10.setOnClickListener {
            crossNum = 60
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.classic11.setOnClickListener {
            crossNum = 61
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.classic12.setOnClickListener {
            crossNum = 62
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.classic13.setOnClickListener {
            crossNum = 63
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.classic14.setOnClickListener {
            crossNum = 64
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.classic15.setOnClickListener {
            crossNum = 65
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.classic16.setOnClickListener {
            crossNum = 66
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.classic17.setOnClickListener {
            crossNum = 67
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.classic18.setOnClickListener {
            crossNum = 68
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.classic19.setOnClickListener {
            crossNum = 69
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }
        binding.classic20.setOnClickListener {
            crossNum = 70
            activity!!.stopService(cService)
            activity!!.stopService(pService)
            toastShow()
            closeFg()
        }

        return binding.root
    }

    private fun toastShow() {

        //visibility
        LocalBroadcastManager.getInstance(activity!!).sendBroadcast(Intent(action))
        //toast
        val toast = Toast.makeText(activity, msg, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
//        val view = toast.view
//        view.setBackgroundColor(Color.GREEN) //any color your want
        toast.show()

        val handler = Handler()
        handler.postDelayed({ toast.cancel() }, 700)
    }

    private fun closeFg() {
        this.findNavController().navigateUp()
    }
}
