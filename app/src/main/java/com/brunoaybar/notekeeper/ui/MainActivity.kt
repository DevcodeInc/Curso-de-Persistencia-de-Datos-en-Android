package com.brunoaybar.notekeeper.ui

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.brunoaybar.notekeeper.R
import com.brunoaybar.notekeeper.persistance.NotesRepository

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import android.view.animation.AlphaAnimation
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.view.ViewAnimationUtils
import android.os.Build
import android.support.v4.content.ContextCompat
import com.brunoaybar.notekeeper.model.Nota
import com.brunoaybar.notekeeper.persistance.CategoriasRepository
import com.brunoaybar.notekeeper.persistance.SettingsRepository
import com.brunoaybar.notekeeper.ui.adapters.NotesAdapter
import android.widget.TextView



class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    private lateinit var currentPage: Pages

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val page = restorePage(savedInstanceState) ?: Pages.Notes
        show(page)

        fab.setOnClickListener { view ->
            NoteActivity.start(this)
        }

        setupDrawer()
    }

    //region Content

    enum class Pages { Notes, Categories, Search;
        companion object {
            fun from(name: String?): Pages? = when(name){
                Notes.name -> Notes
                Categories.name -> Categories
                Search.name -> Search
                else -> null
            }
        }
    }

    private val notesView: NotesView by lazy { NotesView(this) }
    private val searchView: NotesView by lazy { SearchView(this) }
    private val categoriesView: CategoriesView by lazy { CategoriesView(this) }

    private fun show(page: Pages){
        currentPage = page

        //1. Escoger la vista que vamos a mostrar
        val view = when(page){
            Pages.Notes -> notesView
            Pages.Search -> searchView
            Pages.Categories -> categoriesView
        }

        //2. Mostrarla
        with(mainFrameLayout){
            removeAllViews()
            addView(view)
        }

        //3. Actualizar su contenido
        when(view){
            notesView -> actualizarNotas()
            categoriesView -> actualizarCategorias()
        }

        //4. Actualizar titulo
        when(page){
            Pages.Notes -> title = getString(R.string.navigation_option_notas)
            Pages.Categories -> title = getString(R.string.navigation_option_categorias)
            else -> { /* do nothing */ }
        }
    }

    private fun actualizarNotas(){
        notesView.actualizar(NotesRepository.getNotas())
        notesView.listener = object : NotesAdapter.Listener{
            override fun onSelectNote(nota: Nota) {
                NoteActivity.start(this@MainActivity, nota)
            }

            override fun onDownloadNote(nota: Nota) {

            }

            override fun onDeleteNote(nota: Nota) {

            }

        }
        searchItem?.isVisible = true
    }

    private fun actualizarCategorias(){
        categoriesView.actualizar(CategoriasRepository.getCategorias())
        searchItem?.isVisible = false
    }

    private val PARAM_PAGE = "param_page"
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(PARAM_PAGE, currentPage.name)
    }

    private fun restorePage(bundle: Bundle?): Pages?{
        return Pages.from(bundle?.getString(PARAM_PAGE))
    }

    //endregion

    //region Drawer

    private fun setupDrawer() {

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private var searchItem: MenuItem? = null
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(getMenuForCurrentPage(), menu)
        searchItem = menu.findItem(R.id.m_search)
        setupSearch(searchItem)
        return true
    }

    private fun getMenuForCurrentPage() = when(currentPage){
        Pages.Categories -> R.menu.menu_main
        else -> R.menu.menu_main
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_notes -> {
                show(Pages.Notes)
            }
            R.id.nav_categories -> {
                show(Pages.Categories)
            }
            R.id.nav_share -> {

            }
            R.id.nav_settings -> {
                SettingsActivity.start(this)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun loadName(){
        val name = SettingsRepository.getInstance().getSavedName(this)
        val welcomeText = when{
            name.isNullOrBlank() -> getString(R.string.welcome_no_user)
            else -> getString(R.string.welcome_user, name)
        }

        val headerView = nav_view.getHeaderView(0)
        val userTextView = headerView.findViewById(R.id.userTextView) as TextView
        userTextView.text = welcomeText
    }

    override fun onResume() {
        super.onResume()
        loadName()
    }

    //endregion

    //region Search

    private fun setupSearch(searchItem: MenuItem?){
        if(searchItem == null) return

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                // Called when SearchView is collapsing
                if (searchItem.isActionViewExpanded) {
                    animateSearchToolbar(1, false, false)
                    show(Pages.Notes)
                }
                return true
            }

            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                // Called when SearchView is expanding
                animateSearchToolbar(1, true, true)
                show(Pages.Search)
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
