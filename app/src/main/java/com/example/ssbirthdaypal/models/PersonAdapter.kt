package com.example.ssbirthdaypal.models

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ssbirthdaypal.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class PersonAdapter (
    private val items: List<Person>,
    private val onItemClicked: (position: Int) -> Unit
) : RecyclerView.Adapter<PersonAdapter.MyViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item_card, viewGroup, false)
        return  MyViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        viewHolder.textViewTitle.text = items[position].name

        val dateString: String = items[position].birthDayOfMonth.toString() + " " + items[position].birthMonth.toString() + " " + items[position].birthYear.toString()
        val date = parseFlexibleDate(dateString)
        val dateAsString = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(date)
        viewHolder.textViewSubtitle.text = dateAsString

        val daysTil = daysUntilNextBirthday(date)
        viewHolder.textViewBody.text = daysTil.toString()
    }


    class MyViewHolder(itemView: View, private val onItemClicked: (position: Int) -> Unit) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
            val textViewTitle: TextView = itemView.findViewById(R.id.textview_list_item_title)
            val textViewSubtitle: TextView = itemView.findViewById(R.id.textview_list_item_subtitle)
            val textViewBody: TextView = itemView.findViewById(R.id.textview_list_item_body)

            init {
                itemView.setOnClickListener(this)
            }

            override fun onClick(view: View?) {
                val position = bindingAdapterPosition
                onItemClicked(position)
            }
        }



    private fun daysUntilNextBirthday(birthdate: Date?): Int {
        val currentDate = Calendar.getInstance()
        val birthdayCalendar = Calendar.getInstance()
        birthdayCalendar.time = birthdate

        // Set the year of the birthday to the current year
        birthdayCalendar.set(Calendar.YEAR, currentDate.get(Calendar.YEAR))

        // If the birthday has already passed this year, calculate for next year
        if (birthdayCalendar.before(currentDate) || birthdayCalendar == currentDate) {
            birthdayCalendar.add(Calendar.YEAR, 1)
        }

        // Calculate the difference in days
        val timeInMillisUntilBirthday = birthdayCalendar.timeInMillis - currentDate.timeInMillis
        return ((timeInMillisUntilBirthday / (24 * 60 * 60 * 1000)) + 1).toInt()
    }

    fun parseFlexibleDate(dateString: String): Date? {
        val patterns = arrayOf("d M yyyy", "dd MM yyyy")

        for (pattern in patterns) {
            try {
                val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
                dateFormat.isLenient = false
                val date = dateFormat.parse(dateString)
                if (date != null) {
                    return date
                }
            } catch (e: Exception) {
                Log.d("parseDate", e.message.toString())
            }
        }

        return null // Parsing failed
    }
}