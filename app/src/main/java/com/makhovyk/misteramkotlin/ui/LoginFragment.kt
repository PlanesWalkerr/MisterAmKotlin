package com.makhovyk.misteramkotlin.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import com.makhovyk.misteramkotlin.R
import com.makhovyk.misteramkotlin.Utils.SessionManager
import com.makhovyk.misteramkotlin.Utils.Utils
import com.makhovyk.misteramkotlin.data.ApiProvider
import com.makhovyk.misteramkotlin.data.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private var disposable: Disposable? = null
    private lateinit var sessionManager: SessionManager

    private val apiServe by lazy {
        ApiService.create()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        sessionManager = SessionManager(context!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bt_sign_in.setOnClickListener { v: View? ->
            if (validate()) {
                if (sessionManager.isAppRegistered()) {
                    getAuthToken(sessionManager.appToken!!)
                } else {
                    getRegisterToken()
                }
            }
        }
    }

    private fun getRegisterToken() {
        val deviceId = Utils.getDeviceId(activity!!.contentResolver)
        disposable = ApiProvider.provideGetAppToken(deviceId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    val appToken = result.token
                    sessionManager.appToken = appToken
                    Log.v("MisterAm", "app token: "  + appToken)
                    getAuthToken(appToken)
                },
                { error -> Toast.makeText(activity, getString(R.string.error_register), Toast.LENGTH_SHORT).show() }
            )
    }

    private fun getAuthToken(appToken: String) {

        val login = et_login.text.toString()
        val password = et_password.text.toString()
        disposable = ApiProvider.provideGetAuthToken(login, password, appToken)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    val authToken = result.authToken
                    sessionManager.authToken = authToken
                    Log.v("TAG", "authToken: " + authToken)
                    navigateToTaskList()
                },
                { error -> activity?.runOnUiThread { il_auth_error.error = getString(R.string.error_authentication) } }
            )

    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    private fun validate(): Boolean {
        if (!validateLogin()) {
            return false
        }
        if (!validatePassword()) {
            return false
        }
        return true
    }

    private fun validateLogin(): Boolean {
        il_auth_error.isErrorEnabled = false
        if (et_login.text.toString().trim().isEmpty()) {
            il_login.error = getString(R.string.error_login)
            requestFocus(et_login)
            return false
        } else {
            il_login.isErrorEnabled = false
        }
        return true
    }

    private fun validatePassword(): Boolean {
        il_auth_error.isErrorEnabled = false
        if (et_password.text.toString().trim().isEmpty()) {
            il_password.error = getString(R.string.error_password)
            requestFocus(et_password)
            return false
        } else {
            il_password.isErrorEnabled = false
        }
        return true
    }

    private fun requestFocus(view: View) {
        if (view.requestFocus()) {
            activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }
    }

    private fun navigateToTaskList() {
        val intent = Intent(activity, TaskListActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private inner class MyWatcher(val view: View) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            when (view.id) {
                R.id.et_login -> validateLogin()
                R.id.et_password -> validatePassword()
            }
        }

    }
}