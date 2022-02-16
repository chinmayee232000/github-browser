package com.example.calmsleep

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calmsleep.pojo.pojo


class CustomAdapter(private val repo_name: ArrayList<String>,val repo_desc:ArrayList<String>) :
        RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

        /**
         * Provide a reference to the type of views that you are using
         * (custom ViewHolder).
         */
        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var name: TextView
            var desc:TextView
            var repo_share:ImageView

            init {
                // Define click listener for the ViewHolder's View.
                 name = view.findViewById(R.id.repo_name)
                 desc=view.findViewById(R.id.repo_desc)
                 repo_share=view.findViewById(R.id.repo_share)

            }


        }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder  {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.repo_item, viewGroup, false)

        return ViewHolder(view)
    }


        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

            // Get element from your dataset at this position and replace the
            // contents of the view with that element
            viewHolder.name.text = repo_name[position]
            viewHolder.desc.text=repo_desc[position]
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() :Int{
            Log.d(TAG, "getItemCount: "+repo_desc.size+repo_name.size)
            return repo_name.size

        }



}

