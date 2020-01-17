package com.customscopecommunity.crosshairpro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.customscopecommunity.crosshairpro.adapter.PremiumAdapter
import kotlinx.android.synthetic.main.activity_premium.*
import java.util.ArrayList

class PremiumActivity : AppCompatActivity() {

    private var imageList = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_premium)

        initialize()
    }

    private fun initialize() {

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.premium_collection)

        val gridLayoutManager = GridLayoutManager(this, 3)
        premium_recycler_view.layoutManager = gridLayoutManager

        imageList.add(R.drawable.prem1n)
        imageList.add(R.drawable.prem2)
        imageList.add(R.drawable.prem3)
        imageList.add(R.drawable.prem4)
        imageList.add(R.drawable.prem5n)
        imageList.add(R.drawable.prem6n)
        imageList.add(R.drawable.prem7n)
        imageList.add(R.drawable.prem8n)
        imageList.add(R.drawable.prem9n)
        imageList.add(R.drawable.prem10n)
        imageList.add(R.drawable.prem11n)
        imageList.add(R.drawable.prem12n)
        imageList.add(R.drawable.prem13n)
        imageList.add(R.drawable.prem14n)
        imageList.add(R.drawable.prem15n)
        imageList.add(R.drawable.prem16n)
        imageList.add(R.drawable.prem17n)
        imageList.add(R.drawable.prem18n)
        imageList.add(R.drawable.prem19n)
        imageList.add(R.drawable.prem20n)
        imageList.add(R.drawable.prem21n)

        premium_recycler_view.adapter = PremiumAdapter(this, imageList)

    }
}
