package jr.brian.myShop.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import jr.brian.myShop.R
import jr.brian.myShop.databinding.ActivityCategoryBinding
import jr.brian.myShop.model.local.SharedPrefHelper
import jr.brian.myShop.model.remote.category.Category
import jr.brian.myShop.model.remote.category.Inventory
import jr.brian.myShop.model.remote.volley.VolleyHelper
import jr.brian.myShop.presenter.category_presenter.CategoryMVP
import jr.brian.myShop.presenter.category_presenter.CategoryPresenter
import jr.brian.myShop.view.adapter.CategoryAdapter
import jr.brian.myShop.view.auth_fragments.SignUpFragment
import jr.brian.myShop.view.main.LandingActivity

class CategoryActivity : AppCompatActivity(), CategoryMVP.CategoryView {
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var categories: ArrayList<Category>
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var presenter: CategoryPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        supportActionBar?.hide()
    }

    private fun init() {
        categories = ArrayList()
        presenter = CategoryPresenter(VolleyHelper(this), this)
        presenter.getCategories()
        setAdapter(categories)
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
                    startActivity(Intent(this, CartActivity::class.java))
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
        initDrawerHeader()
    }

    private fun initDrawerHeader() {
        val navView = binding.navView.inflateHeaderView(R.layout.nav_header)
        val fullNameTv = navView.findViewById<TextView>(R.id.full_name_tv)
        val emailTv = navView.findViewById<TextView>(R.id.email_tv)
        val mobileNoTv = navView.findViewById<TextView>(R.id.mobileNo_tv)
        SharedPrefHelper(this).apply {
            fullNameTv.text = encryptedSharedPrefs.getString(SignUpFragment.FULL_NAME, "Full Name")
            emailTv.text = encryptedSharedPrefs.getString(SignUpFragment.EMAIL, "Email")
            mobileNoTv.text = encryptedSharedPrefs.getString(SignUpFragment.MOBILE_NO, "Mobile No")
        }
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
        binding.recyclerView.layoutManager =
            GridLayoutManager(this, 2)
        binding.recyclerView.adapter = categoryAdapter
    }

    private fun setAdapter(list: ArrayList<Category>) {
        categoryAdapter = CategoryAdapter(this, list)
        setGridLayout()
    }

    private fun signOut() {
        SharedPrefHelper(this).signOut()
        startActivity(
            Intent(
                this,
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
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawers()
        } else {
            val a = Intent(Intent.ACTION_MAIN).apply {
                addCategory(Intent.CATEGORY_HOME)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(a)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setResult(inventory: Inventory?) {
        if (inventory != null) {
            setAdapter(inventory.categories)
        } else {
            setAdapter(ArrayList())
        }
    }

    override fun onLoad(isLoading: Boolean) {
        when (isLoading) {
            true -> {
                binding.animationView.visibility = View.VISIBLE
                binding.fetchingDataTv.visibility = View.VISIBLE
            }
            else -> {
                binding.animationView.visibility = View.GONE
                binding.fetchingDataTv.visibility = View.GONE
            }
        }
    }
}