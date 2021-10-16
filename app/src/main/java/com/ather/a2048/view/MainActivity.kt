package com.ather.a2048.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompatSideChannelService
import androidx.core.view.GestureDetectorCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ather.a2048.R
import com.ather.a2048.Utils
import com.ather.a2048.databinding.ActivityMainBinding
import com.ather.a2048.databinding.BoxViewBinding
import com.ather.a2048.model.Box
import com.ather.a2048.model.Direction
import com.ather.a2048.model.GameListeners
import com.ather.a2048.viewmodel.MainActivityViewModel
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog


class MainActivity() : AppCompatActivity(), GameListeners {


    companion object {
        val TARGET_SCORE = 2048
        val SIZE: Int = 4
        val SWIPE_MIN_DISTANCE = 120
        val SWIPE_THRESHOLD_VELOCITY = 200
    }

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding
    private var textViews: Array<Array<BoxViewBinding?>> =
        Array<Array<BoxViewBinding?>>(SIZE) { arrayOfNulls<BoxViewBinding>(SIZE) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel;
        initArray()
        viewModel.gridData.observe(this, {
            updateUi(it)
        })
        viewModel.updateHighScore()
        viewModel.isReachedTarget.postValue(false)
        viewModel.isReachedTarget.observe(this, {
            binding.scoreCard.targetReachedTv.text =if (it)  "Target " + TARGET_SCORE.toString() + " Reached" else
                "Target to be reached "+ TARGET_SCORE.toString()
            binding.scoreCard.targetReachedLayout.backgroundTintList =
                resources.getColorStateList(if (it) R.color.success_green else R.color.primaryColor);

        })
        updateBtnText()
    }

    private fun updateUi(grid: Array<Array<Box>>) {
        for (i: Int in 0 until grid.size) {
            for (j: Int in 0 until grid[i].size) {
                if (grid[i][j].value == 0) {
                    textViews[i][j]?.tvValue?.text = ""
                } else {
                    textViews[i][j]?.tvValue?.text = grid[i][j].value.toString()
                }
                setBoxColor(textViews[i][j], grid[i][j])
            }
        }
    }

    private fun setBoxColor(boxViewBinding: BoxViewBinding?, box: Box) {
        var bgColor = Utils.getColor(box.value)
        boxViewBinding?.boxBg?.setCardBackgroundColor(resources.getColor(bgColor))
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


    override fun gameOver(score: Int) {
        AlertDialog.Builder(this)
            .setTitle("Game Over!")
            .setMessage("Game Over! you have scored: " + score.toString() + "") // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setPositiveButton("Ok",
                DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                }) // A null listener allows the button to dismiss the dialog and take no further action.
            .show()

    }

    override fun gameStarted() {
        updateBtnText()
    }

    override fun cantMove(direction: Direction) {
        binding.layoutMain.setBackgroundColor(resources.getColor(R.color.holo_red_dark))
        Handler().postDelayed(Runnable {
            binding.layoutMain.setBackgroundColor(resources.getColor(R.color.trans))
        }, 100)
    }


    fun onResetClicked(v: View) {
        viewModel.reset(this)
        updateBtnText()
    }

    private fun updateBtnText() {
        if (viewModel.checkGameStarted()) {
            binding.fabReset.setImageResource(R.drawable.ic_baseline_replay)
        }
    }

    fun swipe(v: View) {
        if (!viewModel.checkGameStarted()) {
            Toast.makeText(this, "Start Game First", Toast.LENGTH_LONG).show()
            return
        }
        when (v.id) {
            R.id.leftBtn -> {
                viewModel.swipe(Direction.LEFT)
            }
            R.id.rightBtn -> {
                viewModel.swipe(Direction.RIGHT)
            }
            R.id.upBtn -> {
                viewModel.swipe(Direction.UP)
            }
            R.id.downBtn -> {
                viewModel.swipe(Direction.DOWN)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

}