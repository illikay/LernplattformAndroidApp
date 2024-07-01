package com.kayikci.lernplattform2.helpers

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kayikci.lernplattform2.R
import com.kayikci.lernplattform2.activities.ExamDetailActivity
import com.kayikci.lernplattform2.models.Exam

class ExamAdapter(private val examList: List<Exam>) :
    RecyclerView.Adapter<ExamAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.exam = examList[position]
        holder.txvExam.text = examList[position].pruefungsName

        val item: Exam = examList[position]

        if (item.isSelected) {
            // Verändern Sie das Aussehen, um eine Auswahl anzuzeigen
            holder.itemView.setBackgroundColor(Color.LTGRAY)
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT)
        }

        //auf langes drücken reagieren
        holder.itemView.setOnLongClickListener {
            item.isSelected = !item.isSelected
            notifyItemChanged(position)
            true
        }



        holder.itemView.setOnClickListener { v ->
            val context = v.context
            val intent = Intent(context, ExamDetailActivity::class.java)
            intent.putExtra("examId", holder.exam!!.id)
            intent.putExtra("examObject", holder.exam)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return examList.size
    }

    fun getItems() : List<Exam> {
        return examList
    }

    fun getSelectedItems() : List<Exam> {
        examList
        val selectedItems = examList.filter { it.isSelected }
        return selectedItems
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txvExam: TextView = itemView.findViewById(R.id.txv_exam)
        var exam: Exam? = null

        override fun toString(): String {
            return """${super.toString()} '${txvExam.text}'"""
        }
    }
}
