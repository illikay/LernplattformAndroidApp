package com.kayikci.lernplattform2.helpers

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kayikci.lernplattform2.R
import com.kayikci.lernplattform2.activities.QuestionDetailActivity
import com.kayikci.lernplattform2.models.Exam
import com.kayikci.lernplattform2.models.Question

class QuestionAdapter(private val questionList: List<Question>, private val examId: Long, private val examObject: Exam?) :
    RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.questionlist_item, parent, false)


        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val question = questionList[position]

        // sets the text to the textview from our itemHolder class
        holder.textView.text = question.questionFrage

        holder.itemView.setOnClickListener { v ->
            val context = v.context
            val intent = Intent(context, QuestionDetailActivity::class.java)
            intent.putExtra("questionId", question.id)
            intent.putExtra("examId", examId)
            intent.putExtra("examObject", examObject)

            intent.putExtra("questionObject", question)

            context.startActivity(intent)

        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return questionList.size
    }

    fun getQuestionsByExamId() : List<Question> {
        return questionList
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.txv_question)
    }
}
