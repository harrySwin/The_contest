package com.example.thecontest

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    var homeScore: Int = 0
    var awayScore: Int = 0

    override fun onStart() {
        super.onStart()
        Log.i("LIFECYCLE", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("LIFECYCLE", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i("LIFECYCLE", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("LIFECYCLE", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("LIFECYCLE", "onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("LIFECYCLE", "onRestart")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var homeDisplay = findViewById<TextView>(R.id.scoreHome)
        var awayDisplay = findViewById<TextView>(R.id.scoreAway)


        if (savedInstanceState != null) {
            homeScore = savedInstanceState.getInt("HOMESCORE")
            homeDisplay.text = homeScore.toString()
            Toast.makeText(baseContext,homeScore.toString(), Toast.LENGTH_SHORT).show()
            Log.i("LIFECYCLE","restoredSavedInstnace $homeScore")

            awayScore = savedInstanceState.getInt("AWAYSCORE")
            awayDisplay.text = awayScore.toString()
            Toast.makeText(baseContext,awayScore.toString(), Toast.LENGTH_SHORT).show()
            Log.i("LIFECYCLE","restoredSavedInstnace $awayScore")
        }

        //When home team scores
        val home_score = findViewById<Button>(R.id.homeScored)
        home_score.setOnClickListener {
            var homeScoreSound = MediaPlayer.create(this,R.raw.homescore)
            homeScoreSound.start()
            homeScore = homeDisplay.text.toString().toInt() + 1
            homeDisplay.text = homeScore.toString()
            checkWinner(homeScore, awayDisplay.text.toString().toInt())
        }

        //When awway team scores
        val away_score = findViewById<Button>(R.id.awayScored)
        away_score.setOnClickListener {
            var awayScoreSound = MediaPlayer.create(this,R.raw.awayscore)
            awayScoreSound.start()
            awayScore = awayDisplay.text.toString().toInt() + 1
            awayDisplay.text = awayScore.toString()
            checkWinner(homeDisplay.text.toString().toInt(),awayScore)
        }
        //Reset button
        val reset = findViewById<Button>(R.id.reset)
        reset.setOnClickListener {
            reset()
        }
    }
    fun checkWinner(homeScore: Int, awayScore: Int): Boolean {
        if (homeScore == 15) {
            var homeWinSound = MediaPlayer.create(this,R.raw.homewin)
            homeWinSound.start()
            Toast.makeText(this,getResources().getString(R.string.home_wins),Toast.LENGTH_SHORT).show()
            reset()
            return true
        }
        if (awayScore == 15) {
            var awayWinSound = MediaPlayer.create(this,R.raw.awaywin)
            awayWinSound.start()
            Toast.makeText(this,getResources().getString(R.string.away_wins),Toast.LENGTH_SHORT).show()
            reset()
            return true
        }
        else {
            return false
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("HOMESCORE", homeScore)
        Log.i("LIFECYCLE","SaveInstanceState $homeScore")
        outState.putInt("AWAYSCORE", awayScore)
        Log.i("LIFECYCLE","SaveInstanceState $awayScore")
    }
    fun reset() {
        findViewById<TextView>(R.id.scoreHome).text = "0"
        findViewById<TextView>(R.id.scoreAway).text = "0"
    }
}