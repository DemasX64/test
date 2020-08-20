package ru.test.test

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(private val myDataset: MutableCollection<String>) :
    RecyclerView.Adapter<Adapter.MyViewHolder>() {


    class MyViewHolder(val textView:LinearLayout) : RecyclerView.ViewHolder(textView)
        lateinit var jokeTextView:TextView

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): Adapter.MyViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.joke_item, parent, false) as LinearLayout
       jokeTextView = textView.findViewById(R.id.joke_textview)
        return MyViewHolder(textView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       jokeTextView.text = myDataset.elementAt(position)
    }

    fun setItems(jokes: Collection<String>) {
        myDataset.addAll(jokes)
        notifyDataSetChanged()
    }

    override fun getItemCount() = myDataset.size
}