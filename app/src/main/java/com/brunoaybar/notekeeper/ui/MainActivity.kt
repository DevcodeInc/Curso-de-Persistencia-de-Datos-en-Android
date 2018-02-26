package com.brunoaybar.notekeeper.ui

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.brunoaybar.notekeeper.R
import com.brunoaybar.notekeeper.persistance.NotesRepository

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.support.v4.view.MenuItemCompat.isActionViewExpanded
import android.support.v4.view.MenuItemCompat
import android.content.res.TypedArray
import android.content.res.Resources.Theme
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.opengl.ETC1.getHeight
import android.view.animation.TranslateAnimation
import android.view.animation.AlphaAnimation
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.res.Resources
import android.opengl.ETC1.getWidth
import android.view.ViewAnimationUtils
import android.os.Build
import android.support.v4.content.ContextCompat





class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    private lateinit var adapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        adapter = NotesAdapter()
        notasRecyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        notasRecyclerView.adapter = adapter
        actualizarData()

        fab.setOnClickListener { view ->
            NotesRepository.agregar("asdfasdfasf")
            actualizarData()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    private fun actualizarData(){
        adapter.notas = NotesRepository.getNotas()
    }

    //region Drawer

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        setupSearch(menu.findItem(R.id.m_search))
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    //endregion

    //region Search

    private fun setupSearch(searchItem: MenuItem){

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                // Called when SearchView is collapsing
                if (searchItem.isActionViewExpanded) {
                    animateSearchToolbar(1, false, false)
                }
                return true
            }

            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                // Called when SearchView is expanding
                animateSearchToolbar(1, true, true)
                return true
            }
        })
    }

    @SuppressLint("NewApi", "PrivateResource")
    fun animateSearchToolbar(numberOfMenuIcon: Int, containsOverflow: Boolean, show: Boolean) {

        toolbar.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white))
        drawer_layout.setStatusBarBackgroundColor(ContextCompat.getColor(this, R.color.quantum_grey_600))

        if (show) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val width = toolbar.width -
                        (if (containsOverflow) resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material) else 0) -
                        resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) * numberOfMenuIcon / 2
                val createCircularReveal = ViewAnimationUtils.createCircularReveal(toolbar,
                        if (isRtl(resources)) toolbar.width - width else width, toolbar.height / 2, 0.0f, width.toFloat())
                createCircularReveal.duration = 250
                createCircularReveal.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        toolbar.colorize(ContextCompat.getColor(this@MainActivity,android.R.color.darker_gray))
                    }
                })
                createCircularReveal.start()
            } else {
                val translateAnimation = TranslateAnimation(0.0f, 0.0f, -toolbar.height as Float, 0.0f)
                translateAnimation.duration = 220
                toolbar.clearAnimation()
                toolbar.startAnimation(translateAnimation)
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val width = toolbar.width -
                        (if (containsOverflow) resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material) else 0) -
                        resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) * numberOfMenuIcon / 2
                val createCircularReveal = ViewAnimationUtils.createCircularReveal(toolbar,
                        if (isRtl(resources)) toolbar.width - width else width, toolbar.height / 2, width.toFloat(), 0.0f)
                createCircularReveal.duration = 250
                createCircularReveal.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        toolbar.setBackgroundColor(getThemeColor(R.attr.colorPrimary))
                        toolbar.colorize(ContextCompat.getColor(this@MainActivity,android.R.color.white))
                        drawer_layout.setStatusBarBackgroundColor(getThemeColor(R.attr.colorPrimaryDark))
                    }
                })
                createCircularReveal.start()
            } else {
                val alphaAnimation = AlphaAnimation(1.0f, 0.0f)
                val translateAnimation = TranslateAnimation(0.0f, 0.0f, 0.0f, - toolbar.height as Float)
                val animationSet = AnimationSet(true).apply {
                    addAnimation(alphaAnimation)
                    addAnimation(translateAnimation)
                    duration = 220
                }
                animationSet.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation) {

                    }

                    override fun onAnimationEnd(animation: Animation) {
                        toolbar.setBackgroundColor(getThemeColor(R.attr.colorPrimary))
                    }

                    override fun onAnimationRepeat(animation: Animation) {

                    }
                })
                toolbar.startAnimation(animationSet)
            }
            drawer_layout.setStatusBarBackgroundColor(getThemeColor(R.attr.colorPrimaryDark))
        }
    }



    //endregion
}
