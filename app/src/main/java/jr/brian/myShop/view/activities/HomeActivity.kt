package jr.brian.myShop.view.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.*
import com.google.android.material.snackbar.Snackbar
import jr.brian.myShop.R
import jr.brian.myShop.databinding.ActivityHomeBinding
import jr.brian.myShop.databinding.NavHeaderBinding
import jr.brian.myShop.model.local.DatabaseHelper
import jr.brian.myShop.model.local.SharedPrefHelper
import jr.brian.myShop.model.remote.Note
import jr.brian.myShop.model.remote.User
import jr.brian.myShop.view.adapter.NoteAdapter
import jr.brian.myShop.view.auth_fragments.SignUpFragment.Companion.USER
import jr.brian.myShop.view.main.LandingActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var navHeaderBinding: NavHeaderBinding
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var noteList: ArrayList<Note>
    private lateinit var favList: ArrayList<Note>
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
        noteList = ArrayList()
        favList = ArrayList()
        setAdapter(noteList)
        fetchSqlData()
        initFullName()
        initDrawer()
        initListeners()
        initNoteOnSwipe()
    }

    private fun fetchSqlData() {
        var note: Note
        databaseHelper = DatabaseHelper(this)
        val cursor = databaseHelper.getNotes()
        if (cursor != null) {
            if (cursor.count != 0) {
                cursor.moveToFirst()
                note = getCurrentNote(cursor)
                if (note.isStarred == "true") {
                    favList.add(note)
                }
                noteList.add(note)
                while (cursor.moveToNext()) {
                    note = getCurrentNote(cursor)
                    if (note.isStarred == "true") {
                        favList.add(note)
                    }
                    noteList.add(note)
                }
                noteAdapter.notifyItemInserted(noteList.size)
            }
        }
        if (noteList.size < 1) {
            binding.noNotesIv.visibility = View.VISIBLE
        }
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
        }
        binding.fab.setOnClickListener {
            val intent =
                Intent(this, CategoryActivity::class.java)
            intent.putExtra("index", noteList.size)
            startActivity(intent)
        }
    }

    private fun initNoteOnSwipe() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteNote(viewHolder)
            }
        }).attachToRecyclerView(binding.notesRecyclerView)

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteNote(viewHolder)
            }
        }).attachToRecyclerView(binding.notesRecyclerView)
    }

    private fun deleteNote(viewHolder: RecyclerView.ViewHolder) {
        val pos = viewHolder.adapterPosition
        databaseHelper.deleteNote(noteList[pos])
        noteList.removeAt(pos)
        noteAdapter.notifyItemRemoved(pos)
        Snackbar.make(
            binding.notesRecyclerView,
            "Note Deleted",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    @SuppressLint("Range")
    private fun getCurrentNote(cursor: Cursor): Note {
        return Note(
            title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TITLE)),
            body = cursor.getString(cursor.getColumnIndex(DatabaseHelper.BODY)),
            date = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DATE)),
            passcode = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PASSCODE)),
            bodyFontSize = cursor.getString(cursor.getColumnIndex(DatabaseHelper.BODY_FONT_SIZE)),
            textColor = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TEXT_COLOR)),
            isStarred = cursor.getString(cursor.getColumnIndex(DatabaseHelper.IS_STARRED)),
            isLocked = cursor.getString(cursor.getColumnIndex(DatabaseHelper.IS_LOCKED)),
            index = noteList.size
        )
    }

    private fun setGridLayout() {
        binding.notesRecyclerView.layoutManager =
            GridLayoutManager(this@HomeActivity, 2)
        binding.notesRecyclerView.adapter = noteAdapter
    }


    private fun setAdapter(list: List<Note>) {
        noteAdapter = NoteAdapter(this, list)
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