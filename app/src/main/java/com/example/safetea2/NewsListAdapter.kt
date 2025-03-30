package com.example.safetea2

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.safetea2.model.Article
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class NewsListAdapter(private var articles: List<Article>) :
    RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>() {

    // ViewHolder class to hold references to UI elements
    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.newsTitle)
        val description: TextView = itemView.findViewById(R.id.newsDescription)
        val image: ImageView = itemView.findViewById(R.id.newsImage)
        val date: TextView = itemView.findViewById(R.id.newsDate)
        val link: TextView = itemView.findViewById(R.id.newsLink)
    }

    // Inflating the layout for each news item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    // Binding the data to each item in the RecyclerView
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articles[position]
        holder.title.text = article.title
        holder.description.text = article.description ?: "No description available"

        // Loading image using Glide library
        Glide.with(holder.itemView.context)
            .load(article.urlToImage)
            .into(holder.image)

        // Format and display the date
        val formattedDate = article.publishedAt?.let { formatDate(it) } ?: "No date available"
        holder.date.text = formattedDate

        // Handle the link click to open the article in a browser
        holder.link.setOnClickListener {
            val url = article.url
            if (url != null) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    // Returning the total number of items in the list
    override fun getItemCount(): Int = articles.size

    // Updating the list of articles in the adapter
    fun updateArticles(newArticles: List<Article>) {
        articles = newArticles
        notifyDataSetChanged()
    }

    // Function to format the date (optional, using a simple example)
    private fun formatDate(date: String): String {
        // You can use a library like SimpleDateFormat to format the date
        // This is a basic example, adapt it as per your requirement
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.US)
        return try {
            val parsedDate = inputFormat.parse(date)
            outputFormat.format(parsedDate)
        } catch (e: ParseException) {
            "Invalid date"
        }
    }
}
