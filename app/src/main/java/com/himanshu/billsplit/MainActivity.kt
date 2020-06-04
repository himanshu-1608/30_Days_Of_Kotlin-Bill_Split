package com.himanshu.billsplit

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.himanshu.billsplit.databinding.ActivityMainBinding
import com.himanshu.billsplit.navfrags.HomeFragment
import kotlinx.android.synthetic.main.drawer_header.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var sp : SharedPreferences
    private lateinit var abdt: ActionBarDrawerToggle
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        sp = getSharedPreferences("DataFile",Context.MODE_PRIVATE)
        setUpToolbar()
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.navView.getHeaderView(0).txtUserName.text = sp.getString("UserName","")
        abdt = ActionBarDrawerToggle(
            this@MainActivity,
            binding.drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        binding.drawerLayout.addDrawerListener(abdt)
        abdt.syncState()
        binding.navView.setCheckedItem(R.id.home)
        setUpFragment(HomeFragment(),getString(R.string.app_name))
    }

    private fun setUpFragment(whichFragment : Fragment, whichTitle : String) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.frame,
                whichFragment
            )
            .commit()
        supportActionBar?.title = whichTitle
        supportActionBar?.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

}
