package com.makhovyk.misteramkotlin.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.makhovyk.misteramkotlin.R
import com.makhovyk.misteramkotlin.R.string.login
import com.makhovyk.misteramkotlin.R.string.password
import com.makhovyk.misteramkotlin.Utils.SessionManager
import com.makhovyk.misteramkotlin.data.ApiProvider
import com.makhovyk.misteramkotlin.data.ApiService
import com.makhovyk.misteramkotlin.data.Model
import com.makhovyk.misteramkotlin.ui.adapters.TaskListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_active_task_list.*
import kotlinx.android.synthetic.main.fragment_login.*

class TaskListFragment: Fragment() {

    private var disposable: Disposable? = null
    private lateinit var sessionManager: SessionManager
    private val tasks = ArrayList<Model.Task>()

    private val apiServe by lazy {
        ApiService.create()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        sessionManager = SessionManager(context!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_active_task_list, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tasks_recycler_view.layoutManager = LinearLayoutManager(activity)
        tasks_recycler_view.adapter = TaskListAdapter(tasks, this.activity!!)

        if (sessionManager.isLoggedIn()) {
            loadActiveTasks(sessionManager.authToken!!)
        } else {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity!!.finish()
        }

    }

    private fun loadActiveTasks(authToken: String) {
        disposable = ApiProvider.provideGetActiveTasks(authToken)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    tasks.clear()
                    tasks.addAll(result)

                    activity?.runOnUiThread {
                        tasks_recycler_view.adapter.notifyDataSetChanged()
                    }
                },
                { error -> Log.v("MisterAm", error.message)  }
            )
    }
}