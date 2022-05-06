package com.hvdevs.mydailytaxes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView:NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout = findViewById(R.id.root)
        navView = findViewById(R.id.navView)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.first -> {
                    Toast.makeText(this, "Primero", Toast.LENGTH_SHORT).show()
                }
                R.id.second -> {
                    Toast.makeText(this, "Segundo", Toast.LENGTH_SHORT).show()
                }
                R.id.third -> {
                    Toast.makeText(this, "Tercero", Toast.LENGTH_SHORT).show()
                }
                else -> return@OnNavigationItemSelectedListener true
            }
            false
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (!drawerLayout.isDrawerOpen(GravityCompat.START)){
            menuInflater.inflate(R.menu.navigation_menu, menu)
            return true
        }
        return super.onCreateOptionsMenu(menu)
    }
}