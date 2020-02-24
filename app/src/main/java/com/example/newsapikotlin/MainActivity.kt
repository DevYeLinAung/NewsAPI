package com.example.newsapikotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.newsapikotlin.ui.ArticlesListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null){
            val articleFragment = ArticlesListFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.screen_container, articleFragment)
                .commit()
        }
    }
}
