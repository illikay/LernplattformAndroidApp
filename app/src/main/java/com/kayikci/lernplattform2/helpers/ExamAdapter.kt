package com.kayikci.lernplattform2.helpers

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kayikci.lernplattform2.R

import com.kayikci.lernplattform2.activities.ExamDetailActivity
import com.kayikci.lernplattform2.models.Exam

class ExamAdapter(private val destinationList: List<Exam>) : RecyclerView.Adapter<ExamAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

		val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {

		holder.exam = destinationList[position]
		holder.txvDestination.text = destinationList[position].pruefungsName

		holder.itemView.setOnClickListener { v ->
			val context = v.context
			val intent = Intent(context, ExamDetailActivity::class.java)
			intent.putExtra(ExamDetailActivity.ARG_ITEM_ID, holder.exam!!.id)

			context.startActivity(intent)
		}
	}

	override fun getItemCount(): Int {
		return destinationList.size
	}

	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

		val txvDestination: TextView = itemView.findViewById(R.id.txv_question)
		var exam: Exam? = null

		override fun toString(): String {
			return """${super.toString()} '${txvDestination.text}'"""
		}
	}
}
