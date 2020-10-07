package com.customscopecommunity.crosshairpro.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.customscopecommunity.crosshairpro.R
import com.customscopecommunity.crosshairpro.constants.Constants.ACTION
import com.customscopecommunity.crosshairpro.constants.Constants.CROSSHAIR_NUMBER
import com.customscopecommunity.crosshairpro.crossNum
import com.customscopecommunity.crosshairpro.firstOpen
import com.customscopecommunity.crosshairpro.services.MainService
import com.customscopecommunity.crosshairpro.services.PremiumService
import kotlinx.android.synthetic.main.premium_row_layout.view.*


class PremiumAdapter(
    private val context: Context,
    private val imageList: List<Int>
) : RecyclerView.Adapter<PremiumAdapter.PremiumViewHolder>() {

    private val cService = Intent(context, MainService::class.java)
    private val premService = Intent(context, PremiumService::class.java)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PremiumViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.premium_row_layout, parent, false)

        return PremiumViewHolder(view)
    }

    override fun onBindViewHolder(holder: PremiumViewHolder, position: Int) {

        holder.imageCrosshair.setImageResource(imageList[position])

        holder.itemView.setOnClickListener {
            // manage the services here
            crossNum = 80 + position
            stopServices()
            startService()
            changeButtonsVisibility()
            (context as Activity).finish()
        }
    }

    private fun stopServices() {
        context.stopService(cService)
        context.stopService(premService)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    class PremiumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imageCrosshair: ImageView = itemView.premium_crosshair
    }

    private fun changeButtonsVisibility() {
        LocalBroadcastManager.getInstance(context).sendBroadcast(Intent(ACTION))
    }

    private fun startService(){

        if (firstOpen){
            Toast.makeText(context, context.getString(R.string.please_wait), Toast.LENGTH_SHORT).show()
            firstOpen = false
        }

        premService.putExtra(CROSSHAIR_NUMBER, crossNum)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(premService)
        } else {
            context.startService(premService)
        }
    }
}