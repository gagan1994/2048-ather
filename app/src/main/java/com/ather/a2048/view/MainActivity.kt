package com.ather.a2048.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ather.a2048.databinding.BoxViewBinding
import com.ather.a2048.model.Direction

import android.view.GestureDetector
import android.view.MotionEvent
import androidx.core.view.GestureDetectorCompat
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ather.a2048.R
import com.ather.a2048.databinding.ActivityMainBinding
import com.ather.a2048.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener {


    companion object {
        val SWIPE_MIN_DISTANCE = 120
        val SWIPE_THRESHOLD_VELOCITY = 200
        val SIZE = 4
    }

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var mGestureDetector: GestureDetectorCompat
    private var textViews: Array<Array<BoxViewBinding?>> =
        Array<Array<BoxViewBinding?>>(SIZE) { arrayOfNulls<BoxViewBinding>(SIZE) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel;
        mGestureDetector = GestureDetectorCompat(this, this)
        initArray()
    }


    private fun initArray() {
        textViews[0][0] = binding.row0.box0
        textViews[0][1] = binding.row0.box1
        textViews[0][2] = binding.row0.box2
        textViews[0][3] = binding.row0.box3

        textViews[1][0] = binding.row1.box0
        textViews[1][1] = binding.row1.box1
        textViews[1][2] = binding.row1.box2
        textViews[1][3] = binding.row1.box3

        textViews[2][0] = binding.row2.box0
        textViews[2][1] = binding.row2.box1
        textViews[2][2] = binding.row2.box2
        textViews[2][3] = binding.row2.box3

        textViews[3][0] = binding.row3.box0
        textViews[3][1] = binding.row3.box1
        textViews[3][2] = binding.row3.box2
        textViews[3][3] = binding.row3.box3
    }


    fun onResetClicked(v: View) {
        viewModel.reset()
        updateBtnText()
    }

    private fun updateBtnText() {
        if (viewModel.checkGameStarted()) {
            binding.fabReset.setImageResource(R.drawable.ic_baseline_replay)
        }
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
        if (!viewModel.checkGameStarted()) {
            Toast.makeText(this, "Start Game First", Toast.LENGTH_LONG).show()
            return true
        }
        if (e1!!.x - e2!!.x > SWIPE_MIN_DISTANCE
            && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY
        ) {
            viewModel.swipe(Direction.LEFT)
        } else if (e2.x - e1.x > SWIPE_MIN_DISTANCE
            && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY
        ) {
            viewModel.swipe(Direction.RIGHT)
        } else if (e1.y - e2.y > SWIPE_MIN_DISTANCE
            && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY
        ) {
            viewModel.swipe(Direction.UP)
        } else if (e2.y - e1.y > SWIPE_MIN_DISTANCE
            && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY
        ) {
            viewModel.swipe(Direction.DOWN)
        }
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