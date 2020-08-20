package ru.test.test

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*

class JokesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var button: Button
    private lateinit var editText: EditText

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_jokes, container, false)

        val dataset:MutableCollection<String> = mutableListOf()
        viewManager = LinearLayoutManager(activity)
        viewAdapter = Adapter(dataset)

        recyclerView = root.findViewById<RecyclerView>(R.id.list_of_jokes).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        button = root.findViewById(R.id.button)
        button.setOnClickListener{
            if(editText.text.isNotEmpty()) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.icndb.com")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val jsonPlaceHolderApi: JSONPlaceholderApi =
                    retrofit.create(JSONPlaceholderApi::class.java)
                val call: Call<String> =
                    jsonPlaceHolderApi.getJokes(editText.text.toString().toInt())

                call.enqueue(object : Callback<String> {
                    override fun onFailure(call: Call<String>?, t: Throwable?) {
                        Log.v("retrofit", t.toString())
                    }

                    override fun onResponse(call: Call<String>?, response: Response<String>?) {
                        if (response != null) {

                            val jokes: Collection<String> = getJokes(response.body())

                            recyclerView.adapter = Adapter(jokes as MutableCollection<String>)
                        }
                    }
                })
            }
            else
                Toast.makeText(activity,"enter count",Toast.LENGTH_SHORT).show()
        }

        editText = root.findViewById(R.id.edittext)

        return root
    }

    fun getJokes(input: String?): Collection<String> {
        val jokes: ArrayList<String> = ArrayList<String>()
        try {
            val obj = JSONObject(input)
            val arr = obj.getJSONArray("value")
            for (i in 0 until arr.length()) {
                val joke = arr.getJSONObject(i)
                jokes.add(joke.getString("joke"))
            }
        } catch (e: JSONException) {
            Log.d("err", e.toString())
        }
        return jokes
    }
}