package com.ather.a2048.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ather.a2048.R
import com.ather.a2048.databinding.ActivityMainBinding
import com.ather.a2048.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener {


    companion object {
        val SWIPE_MIN_DISTANCE = 120
        val SWIPE_THRESHOLD_VELOCITY = 200
    }

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var mGestureDetector: GestureDetectorCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel;
        mGestureDetector = GestureDetectorCompat(this, this)

    }

    fun onResetClicked(v: View) {
        viewModel.reset()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mGestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }


    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {

        // right to left swipe
        if (e1!!.x - e2!!.x > SWIPE_MIN_DISTANCE
            && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY
        ) {
            viewModel.onLeftSwipe()
        } else if (e2.x - e1.x > SWIPE_MIN_DISTANCE
            && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY
        ) {
            viewModel.onRightSwipe()
        } else if (e1.y - e2.y > SWIPE_MIN_DISTANCE
            && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY
        ) {
            viewModel.onUpSwipe()
        } else if (e2.y - e1.y > SWIPE_MIN_DISTANCE
            && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY
        ) {
            viewModel.onDownSwipe()
        }
        viewModel.updateSwipes()
        return true
    }



    /*
    * Ignore below functions
    * */

    override fun onDown(e: MotionEvent?): Boolean {
        return false

    }

    override fun onShowPress(e: MotionEvent?) {
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent?) {
    }

}