package jr.brian.myShop.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import jr.brian.myShop.R
import jr.brian.myShop.databinding.ActivityHomeBinding
import jr.brian.myShop.databinding.NavHeaderBinding
import jr.brian.myShop.model.local.SharedPrefHelper
import jr.brian.myShop.model.remote.Category
import jr.brian.myShop.model.remote.User
import jr.brian.myShop.view.adapter.CategoryAdapter
import jr.brian.myShop.view.auth_fragments.SignUpFragment.Companion.USER
import jr.brian.myShop.view.main.LandingActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var navHeaderBinding: NavHeaderBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var categories: ArrayList<Category>
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var fullName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        navHeaderBinding = NavHeaderBinding.inflate(layoutInflater)
        navHeaderBinding.animationView.setMinAndMaxFrame(67, 120)
        setContentView(binding.root)
        init()
        supportActionBar?.hide()
    }

    private fun init() {
        categories = ArrayList()
        for (i in 1..6) {
            categories.add(
                Category(
                    "1",
                    "",
                    "",
                    ""
                )
            )
        }
        setAdapter(categories)
        initFullName()
        initDrawer()
        initListeners()
    }

    private fun initDrawer() {
        toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            R.string.open, R.string.close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.drawer_home -> {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.drawer_cart -> {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.drawer_orders -> {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.drawer_profile -> {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.drawer_sign_out -> {
                    signOut()
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
            }
            true
        }
    }

    private fun initFullName() {
        fullName =
            intent.extras?.getParcelable<User>(USER)?.fullName ?: "Full Name"
        navHeaderBinding.fullNameTv.text = fullName
    }

    private fun initListeners() {
        binding.menu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
            showSearchCursor(false)
        }
        binding.homeSearchEt.setOnClickListener {
            showSearchCursor(true)
        }
        binding.search.setOnClickListener {
            showSearchCursor(false)
        }
    }

    private fun setGridLayout() {
        binding.notesRecyclerView.layoutManager =
            GridLayoutManager(this@HomeActivity, 2)
        binding.notesRecyclerView.adapter = categoryAdapter
    }

    private fun setAdapter(list: List<Category>) {
        categoryAdapter = CategoryAdapter(this, list)
        setGridLayout()
    }

    private fun signOut() {
        SharedPrefHelper(this).signOut()
        startActivity(
            Intent(
                this@HomeActivity,
                LandingActivity::class.java
            )
        )
        finish()
    }

    private fun showSearchCursor(bool: Boolean) {
        binding.homeSearchEt.apply {
            isCursorVisible = bool
        }
    }

    override fun onBackPressed() {
        val a = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(a)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}