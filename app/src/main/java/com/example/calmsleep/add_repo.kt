package com.example.calmsleep

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class add_repo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_repo)
        var add:Button=findViewById(R.id.button)
        var name:TextView=findViewById(R.id.repo)
        var back:TextView=findViewById(R.id.back)
        add.setOnClickListener(object:View.OnClickListener{
            override fun onClick(view: View?) {
                Log.d(TAG, "onClick: ")
                if(name.text.isNotBlank()) {
                    val preference = getSharedPreferences("add_repo", Context.MODE_PRIVATE)
                    val editor = preference.edit()
                    editor.putString("repo_name", name.text.toString())
                    editor.commit()
                    Toast.makeText(this@add_repo, "Repository stored locally", Toast.LENGTH_SHORT)
                        .show()
                }
                else{
                    Toast.makeText(this@add_repo, "Repository name is mandatory", Toast.LENGTH_SHORT)
                }
            }

        })


        back.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                var intent= Intent(this@add_repo,MainActivity::class.java)
                startActivity(intent)
            }

        })
    }
}