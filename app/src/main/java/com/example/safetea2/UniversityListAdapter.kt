package com.example.safetea2

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.safetea2.model.University

class UniversityListAdapter(private var universities: List<University>) :
    RecyclerView.Adapter<UniversityListAdapter.UniversityViewHolder>() {

    class UniversityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.universityName)
        //val countryTextView: TextView = itemView.findViewById(R.id.universityCountry)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UniversityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_university, parent, false)
        return UniversityViewHolder(view)
    }

    override fun onBindViewHolder(holder: UniversityViewHolder, position: Int) {
        val university = universities[position]
        holder.nameTextView.text = university.name
        //holder.countryTextView.text = university.country

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, UniversityDetailsActivity::class.java)
            intent.putExtra("UNIVERSITY_NAME", university.name)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = universities.size

    fun updateData(newUniversities: List<University>) {
        universities = newUniversities
        notifyDataSetChanged()
    }
}
