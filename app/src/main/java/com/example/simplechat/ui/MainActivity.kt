package com.example.simplechat.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.core.domain.User
import com.example.simplechat.R
import com.example.simplechat.ui.chat.ChatFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val users = Pair(User(1, "Sarah"), User(2, "Jerry"))
    private var currentUserId: Int = users.first.id

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        val actionBar = supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false)

        if (savedInstanceState == null) {
            loadChatForUsers(users)
            updateNavBar(users)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_s, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.switch_action -> {
            Toast.makeText(applicationContext, "Switching user", Toast.LENGTH_LONG).show()
            loadAnotherUser()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun loadAnotherUser() {
        changeCurrentUserId()
        val newUserPair = if (currentUserId == 1) {
            Pair(users.first, users.second)
        } else {
            Pair(users.second, users.first)
        }
        updateNavBar(newUserPair)
        loadChatForUsers(newUserPair)
    }

    private fun loadChatForUsers(newUserPair: Pair<User, User>) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ChatFragment.newInstance(newUserPair.first.id, newUserPair.second.id))
            .commitNow()
    }

    private fun changeCurrentUserId() {
        currentUserId = if (currentUserId == 1) {
            users.second.id
        } else {
            users.first.id
        }
    }

    private fun updateNavBar(users: Pair<User, User>) {
        findViewById<TextView>(R.id.tvUserName).text = users.second.name
        findViewById<TextView>(R.id.tvUserSignature).text = users.second.name.subSequence(0, 1)
    }
}
