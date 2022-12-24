package com.shamweel.livereaction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val reactionViewOne = findViewById<LiveReactionView>(R.id.liveReactionView)
        val fabOne = findViewById<FloatingActionButton>(R.id.fab)

        fabOne.setOnClickListener {
            reactionViewOne.performLiveReaction(R.drawable.ic_heart_filled_red)
        }

        val reactionViewSecond = findViewById<LiveReactionView>(R.id.liveReactionViewSecond)
        val fabSecond = findViewById<FloatingActionButton>(R.id.fabSecond)

        fabSecond.setOnClickListener {
            reactionViewSecond.performLiveReaction(R.drawable.ic_heart_filled_yellow)
        }


        val reactionViewThird = findViewById<LiveReactionView>(R.id.liveReactionViewThird)
        val fabThird = findViewById<FloatingActionButton>(R.id.fabThird)

        fabThird.setOnClickListener {
            reactionViewThird.performLiveReaction(R.drawable.ic_heart_filled_purple)
        }


        val reactionViewFourth = findViewById<LiveReactionView>(R.id.liveReactionViewFourth)
        val faFourth = findViewById<FloatingActionButton>(R.id.fabFourth)

        faFourth.setOnClickListener {
            val drawableRes = listOf(
                R.drawable.ic_heart_filled_red,
                R.drawable.ic_heart_filled_purple,
                R.drawable.ic_heart_filled_yellow,
                R.drawable.ic_heart_filled_green,
                R.drawable.ic_demo_1,
                R.drawable.ic_demo_2,
                R.drawable.ic_demo_3,
                R.drawable.ic_demo_4,
                R.drawable.ic_demo_5,
                R.drawable.ic_demo_6,
                R.drawable.ic_demo_7,
                R.drawable.ic_demo_8,
                R.drawable.ic_demo_9
            ).random()
            reactionViewFourth.performLiveReaction(drawableRes)
        }


    }
}