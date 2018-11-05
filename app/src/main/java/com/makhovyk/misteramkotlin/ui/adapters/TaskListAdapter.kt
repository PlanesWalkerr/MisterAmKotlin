package com.makhovyk.misteramkotlin.ui.adapters

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.makhovyk.misteramkotlin.R
import com.makhovyk.misteramkotlin.Utils.Utils
import com.makhovyk.misteramkotlin.data.Model
import java.text.DateFormat
import java.text.SimpleDateFormat
import kotlinx.android.synthetic.main.item_task.view.*

class TaskListAdapter(val tasks: List<Model.Task>, val context: Context) :
    RecyclerView.Adapter<TaskListAdapter.TaskHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_task, parent, false)
        return TaskHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.bindTask(tasks[position])
    }


    inner class TaskHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindTask(task: Model.Task) {

            // handling receiving order from company
            val companyOrder = task.orders[0]
            val companyTimeString = SimpleDateFormat("hh:mm").format(companyOrder.time)
            val companyOrderId = companyOrder.id.toString()
            view.tv_company_order_time.text = companyTimeString
            view.tv_company_order_id.text = companyOrderId
            view.tv_company_order_address_title.text = companyOrder.address.title
            view.tv_company_order_address_description.text = companyOrder.address.description
            view.tv_company_order_amount.text = context.getString(R.string.amount, companyOrder.amount.toString())
            setColorDependingOnAmount(view.tv_company_order_amount,companyOrder.amount)
            addTagsImages(companyOrder.tags, view.ll_company_order_tags)

            // handling delivering order from user
            val userOrder = task.orders[1]
            val userTimeString = SimpleDateFormat("hh:mm").format(userOrder.time)
            val userOrderId = userOrder.id.toString()
            view.tv_user_order_time.text = userTimeString
            view.tv_user_order_id.text = userOrderId
            view.tv_user_order_address_title.text = userOrder.address.title
            view.tv_user_order_address_description.text = userOrder.address.description
            view.tv_user_order_amount.text = context.getString(R.string.amount, userOrder.amount.toString())
            setColorDependingOnAmount(view.tv_user_order_amount,userOrder.amount)
            addTagsImages(userOrder.tags, view.ll_user_order_tags)
        }

        fun setColorDependingOnAmount(tv: TextView, amount: Float) {
            if (amount < 0) {
                tv.setTextColor(ContextCompat.getColor(context, R.color.colorRed))
            } else if (amount > 0) {
                tv.setTextColor(ContextCompat.getColor(context, R.color.colorGreen))
            }
        }

        fun addTagsImages(tags: List<String>, ll: LinearLayout) {
            // dynamically adding tags images

            ll.removeAllViews()
            tags.forEach {
                val imageView = ImageView(context)
                imageView.setImageResource(Utils.getTagDrawableResource(it))
                imageView.setPadding(10, 10, 10, 10)
                ll.addView(imageView)
            }
        }
    }
}