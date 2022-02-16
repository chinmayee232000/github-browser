package com.example.calmsleep

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calmsleep.pojo.pojo
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.json.JSONObject
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory




class MainActivity : AppCompatActivity() {
     val BASE_URL="https://api.github.com/users/"
    lateinit var recyclerview:RecyclerView
    lateinit var adapter: CustomAdapter
    lateinit var add:TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        add=findViewById(R.id.add)

        converter()
        add.setOnClickListener(View.OnClickListener {
            var next= Intent(this,add_repo::class.java)
            startActivity(next)
        })
         recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.hasFixedSize()

    }

    private fun converter() {

        var okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(
                    Interceptor { chain ->
                        val builder = chain.request().newBuilder()
                        builder.header("Accept", "application/vnd.github.v3+json")
                        return@Interceptor chain.proceed(builder.build())
                    }
            )
        }.build()




        val retrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL).build().create(ApiInterface::class.java)
        val retrofitData=retrofitBuilder.getData()
        retrofitData.enqueue(object : Callback<ArrayList<pojo>> {


            override fun onFailure(call: Call<ArrayList<pojo>>?, t: Throwable?) {
                Log.d(TAG, "onFailure: ")
            }

            override fun onResponse(call: Call<ArrayList<pojo>>?, response: Response<ArrayList<pojo>>?) {
                var data=arrayListOf<pojo>()
                var name= arrayListOf<String>()
                var desc= arrayListOf<String>()
                val preference=getSharedPreferences("add_repo", Context.MODE_PRIVATE)
                val added_repo= preference.getString("repo_name","0")

            if (response != null) {
                if(response.isSuccessful) {
                    Log.d(TAG, "onResponse: " + response.body()?.size)
                    var i=response.body().size-1
                    var repo:pojo

                    //data.addAll(response.body())

                    while(i>=0) {
                        repo = response.body().get(i)
                        Log.d(TAG, "onResponse: repo"+repo)
                        data.add(repo)
                        name.add(repo.name)
                        desc.add(repo.description)
                        i--
                    }

                    if(added_repo!="0"&& added_repo!=null) {
                        Log.d(TAG, "onResponse: added_repo"+added_repo)
                        name.add(added_repo)
                        desc.add("")
                    }


                    Log.d(TAG, "onResponse: customadapter"+name.size+name)
                    //recyclerview.layoutManager = LinearLayoutManager()
                    adapter = CustomAdapter(name,desc)
                    adapter.notifyDataSetChanged()
                    recyclerview.adapter = adapter

                }
                else {
                    try {
                        Log.d(TAG, "onResponse code: "+response.code())
                        val jObjError = JSONObject(response.errorBody().string())
                        Log.d(TAG, "onResponse try: "+ jObjError.getJSONObject("error").getString("message"))
                    } catch (e: Exception) {
                        Log.d(TAG, "onResponse catch: "+e.message)                        }
                }
            }

        }



})
}

}