package com.customscopecommunity.crosshairpro.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.customscopecommunity.crosshairpro.R
import com.customscopecommunity.crosshairpro.adapter.ProAdapter
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.fragment_pro.*
import java.util.ArrayList


class ProActivity : AppCompatActivity() {

    private var imageList = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_pro)

        initialize()

        proBannerAd.loadAd(AdRequest.Builder().build())

    }

    private fun initialize() {

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.pro_collection)

        val gridLayoutManager = GridLayoutManager(this, 4)
        pro_recycler_view.layoutManager = gridLayoutManager

        imageList.add(R.drawable.pro1n)
        imageList.add(R.drawable.pro2n)
        imageList.add(R.drawable.pro3n)
        imageList.add(R.drawable.pro4n)
        imageList.add(R.drawable.pro5n)
        imageList.add(R.drawable.pro6n)
        imageList.add(R.drawable.pro7n)
        imageList.add(R.drawable.pro8n)
        imageList.add(R.drawable.pro9n)
        imageList.add(R.drawable.pro10n)
        imageList.add(R.drawable.pro11n)
        imageList.add(R.drawable.pro12n)
        imageList.add(R.drawable.pro13n)
        imageList.add(R.drawable.pro14n)
        imageList.add(R.drawable.pro15n)
        imageList.add(R.drawable.pro16n)
        imageList.add(R.drawable.pro17n)
        imageList.add(R.drawable.pro18n)
        imageList.add(R.drawable.pro19n)
        imageList.add(R.drawable.pro20n)

        pro_recycler_view.adapter = ProAdapter(this, imageList)

    }
}
