package com.example.guardianapi.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.guardianapi.R
import com.example.guardianapi.databinding.ActivityMainBinding
import com.example.guardianapi.ui.article.savedarticles.SavedArticlesFragment
import com.example.guardianapi.ui.article.searcharticle.SearchArticleFragment
import com.example.guardianapi.ui.mainfragment.MainFragment
import com.example.guardianapi.ui.weather.WeatherFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setCurrentFragment(MainFragment())
        binding.bottomNavigation.selectedItemId = R.id.menu_news

        binding.bottomNavigation.setOnItemSelectedListener { menu ->
            when(menu.itemId){
                R.id.menu_news -> {
                    setCurrentFragment(MainFragment())
                    binding.toolbarGuardian.visibility = View.VISIBLE
                    binding.ivInfo.visibility = View.GONE
                }
                R.id.menu_saved_articles -> {
                    setCurrentFragment(SavedArticlesFragment())
                    binding.toolbarGuardian.visibility = View.VISIBLE
                    binding.ivInfo.visibility = View.VISIBLE
                }
                R.id.menu_weather -> {
                    setCurrentFragment(WeatherFragment())
                    binding.toolbarGuardian.visibility = View.GONE
                }
                R.id.menu_search -> {
                    setCurrentFragment(SearchArticleFragment())
                    binding.toolbarGuardian.visibility = View.GONE
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_fragment, fragment)
            .commit()
    }

    override fun onResume() {
        super.onResume()
        setCurrentFragment(MainFragment())
        binding.bottomNavigation.selectedItemId = R.id.menu_news
    }

}