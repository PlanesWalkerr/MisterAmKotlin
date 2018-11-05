package com.makhovyk.misteramkotlin.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.makhovyk.misteramkotlin.R
import com.makhovyk.misteramkotlin.Utils.SessionManager
import kotlinx.android.synthetic.main.activity_task_list.*

class TaskListActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
        title = getString(R.string.label_active_tasks)
        navigationView.itemIconTintList = null
        navigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_orders -> {
                    Toast.makeText(applicationContext, getString(R.string.orders), Toast.LENGTH_SHORT).show()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_notifications -> {
                    Toast.makeText(applicationContext, getString(R.string.notifications), Toast.LENGTH_SHORT).show()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    showConfirmDialog()
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }


        val taskListFragment = TaskListFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.main_container, taskListFragment)
            .commit()
    }

    private fun showConfirmDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.logout))
            .setMessage(R.string.logout_message)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton(getString(R.string.ok_message)){dialog, which ->
                SessionManager(this).logOut()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            .setNegativeButton(getString(R.string.cancel_message), null)
            .create()
            .show()
    }
}