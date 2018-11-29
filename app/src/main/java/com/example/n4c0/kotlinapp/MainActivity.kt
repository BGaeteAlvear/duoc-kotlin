package com.example.n4c0.kotlinapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.example.mylibrary.ToolbarActivity
import com.example.n4c0.kotlinapp.adapters.PagerAdapter
import com.example.n4c0.kotlinapp.fragments.ChatFragment
import com.example.n4c0.kotlinapp.fragments.RatesFragment
import com.example.n4c0.kotlinapp.fragments.infoFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : ToolbarActivity() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    private lateinit var adapter: PagerAdapter
    private var prevBottomSelected: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

        toolbarToLoad(toolbarView as Toolbar)

        setUpViewPager(getPagerAdapter())
        setupButtonNavigationBar()

  }

    private fun getPagerAdapter(): PagerAdapter{
        val adapter = PagerAdapter(supportFragmentManager)
        adapter.addFragment(infoFragment())
        adapter.addFragment(RatesFragment())
        adapter.addFragment(ChatFragment())
        return adapter
    }

    private fun setUpViewPager(adapter: PagerAdapter){
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {

                if (prevBottomSelected == null){
                    bottomNavigation.menu.getItem(0).isChecked = false
                }else{
                    prevBottomSelected!!.isChecked = false
                }
                bottomNavigation.menu.getItem(position).isChecked = true
                prevBottomSelected = bottomNavigation.menu.getItem(position)
            }

        })
    }

    private fun setupButtonNavigationBar(){
        bottomNavigation.setOnNavigationItemReselectedListener { item ->
            when(item.itemId){
                R.id.bottom_nav_info ->{
                    viewPager.currentItem = 0; true
                }

                R.id.bottom_nav_rate ->{
                    viewPager.currentItem = 1; true
                }

                R.id.bottom_nav_chat ->{
                    viewPager.currentItem = 2; true
                }
                else -> false
            }
        }
    }
}
