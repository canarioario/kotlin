package com.example.playaudio2

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.SeekBar
import com.example.playaudio2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var mp: MediaPlayer? = null
    private var currentSong = mutableListOf(R.raw.musicrr)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        controlSound(currentSong[0])
    }

    private fun controlSound(id: Int) {

        binding.fabPlay.setOnClickListener {
            if (mp == null) {
                mp = MediaPlayer.create(this, id)
                Log.d("MainActivity", "ID: ${mp?.audioSessionId}")

                initialiseSeekbar()
            }
            mp?.start()
            Log.d("MainActivity", "Duration: ${mp?.duration?.div(1000)} seconds")

        }

        binding.fabPause.setOnClickListener {
            if (mp != null) mp?.pause()
            Log.d("MainActivity", "Pauset at: ${mp?.currentPosition?.div(1000)} seconds")
        }

        binding.fabStop.setOnClickListener {
            if (mp !== null) {
                mp?.stop()
                mp?.reset()
                mp?.release()
                mp = null
            }
        }

        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                if (fromUser) mp?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
        })
    }


private fun initialiseSeekbar() {
    binding.seekbar.max = mp?.duration ?: 0

    val handler = Handler()
    handler.postDelayed(object : Runnable {
        override fun run() {
            try {
                binding.seekbar.progress = mp?.currentPosition ?: 0
                handler.postDelayed(this, 1000)
            } catch (e: Exception) {
                binding.seekbar.progress = 0
            }
        }

    }, 0)
}
}