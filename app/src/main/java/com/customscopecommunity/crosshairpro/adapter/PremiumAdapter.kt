package com.customscopecommunity.crosshairpro.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.customscopecommunity.crosshairpro.R
import com.customscopecommunity.crosshairpro.crossNum
import com.customscopecommunity.crosshairpro.isCrosshairSelected
import com.customscopecommunity.crosshairpro.screens.action
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
            showToast()
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

    inner class PremiumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        var imageCrosshair: ImageView = itemView.premium_crosshair


    }

    private fun showToast() {
        isCrosshairSelected = true
        LocalBroadcastManager.getInstance(context).sendBroadcast(Intent(action))
    }
}