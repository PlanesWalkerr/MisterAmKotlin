package com.makhovyk.misteramkotlin.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.makhovyk.misteramkotlin.R
import kotlinx.android.synthetic.main.activity_login.view.*

class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginFragment = LoginFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.main_container, loginFragment)
            .commit()
    }
}