package com.kayikci.lernplattform2.helpers

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kayikci.lernplattform2.R


import com.kayikci.lernplattform2.activities.QuestionDetailActivity


import com.kayikci.lernplattform2.models.Question

class QuestionAdapter(private val questionList: List<Question>) : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.questionlist_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.question = questionList[position]
        holder.txvDestination.text = questionList[position].questionFrage

        holder.itemView.setOnClickListener { v ->
            val context = v.context
            val intent = Intent(context, QuestionDetailActivity::class.java)
            intent.putExtra("questionId", holder.question!!.id)


            context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return questionList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txvDestination: TextView = itemView.findViewById(R.id.txv_question)
        var question: Question? = null

        override fun toString(): String {
            return """${super.toString()} '${txvDestination.text}'"""
        }
    }
}
