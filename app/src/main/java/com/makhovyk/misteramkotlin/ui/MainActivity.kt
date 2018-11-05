package com.makhovyk.misteramkotlin.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.makhovyk.misteramkotlin.R
import com.makhovyk.misteramkotlin.Utils.SessionManager
import com.makhovyk.misteramkotlin.Utils.Utils
import com.makhovyk.misteramkotlin.data.ApiProvider
import com.makhovyk.misteramkotlin.data.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sessionManager = SessionManager(this)
        if (!sessionManager.isLoggedIn()) {
            Log.v("MisterAm", "not logged")
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Log.v("MisterAm", "logged, token: "  + sessionManager.authToken)
            Log.v("MisterAm", "register token: "  + sessionManager.appToken)
            val intent = Intent(this, TaskListActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}
