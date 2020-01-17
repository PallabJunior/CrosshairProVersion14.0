package com.customscopecommunity.crosshairpro.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.customscopecommunity.crosshairpro.R
import com.customscopecommunity.crosshairpro.crossNum
import com.customscopecommunity.crosshairpro.screens.action
import com.customscopecommunity.crosshairpro.services.MainService
import com.customscopecommunity.crosshairpro.services.PremiumService
import com.customscopecommunity.crosshairpro.services.ProService
import kotlinx.android.synthetic.main.row_layout.view.*


class ProAdapter(
    private val context: Context,
    private val imageList: List<Int>
) : RecyclerView.Adapter<ProAdapter.CrosshairViewHolder>() {

    private val cService = Intent(context, MainService::class.java)
    private val pService = Intent(context, ProService::class.java)
    private val premiumService = Intent(context, PremiumService::class.java)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrosshairViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)

        return CrosshairViewHolder(view)
    }

    override fun onBindViewHolder(holder: CrosshairViewHolder, position: Int) {

        holder.imageCrosshair.setImageResource(imageList[position])

        holder.itemView.setOnClickListener {

            crossNum = position
            stopServices()
            showToast()
            (context as Activity).finish()
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    inner class CrosshairViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imageCrosshair: ImageView = itemView.image_crosshair

    }

    private fun stopServices() {
        context.stopService(cService)
        context.stopService(pService)
        context.stopService(premiumService)
    }

    private fun showToast() {
        LocalBroadcastManager.getInstance(context).sendBroadcast(Intent(action))
        val toast = Toast.makeText(context, context.getString(R.string.tap_start), Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()

        val handler = Handler()
        handler.postDelayed({ toast.cancel() }, 700)
    }
}
