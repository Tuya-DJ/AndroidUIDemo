package com.tuya.smart.ipc.androiduiwidget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        findViewById<Button>(R.id.swipe_btn).setOnClickListener(View.OnClickListener {

            var intent = Intent(this,SwipeRecyclerViewActivity::class.java)
            startActivity(intent)
        })

        findViewById<Button>(R.id.gallery_snap_btn).setOnClickListener(View.OnClickListener {

            var intent = Intent(this,GallerySnaperActivity::class.java)
            startActivity(intent)
        })
    }
}
