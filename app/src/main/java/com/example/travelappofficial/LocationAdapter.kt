package com.example.travelappofficial

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault

class LocationAdapter (var locationList: MutableList<LocationEntry>) : RecyclerView.Adapter<LocationAdapter.ViewHolder>()  {
    companion object {
        val TAG = "LocationAdapter"
        val EXTRA_LOCATION_UPDATE = "location entry"
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView
        val ratingBar: RatingBar
        val textViewMoneySpent: TextView
        val textViewEmotion: TextView
        val layout: ConstraintLayout

        init {
            textViewName = itemView.findViewById(R.id.textView_locationItem_locationName)
            textViewMoneySpent = itemView.findViewById(R.id.textView_locationItem_price)
            textViewEmotion = itemView.findViewById(R.id.textView_locationItem_emoji)
            ratingBar = itemView.findViewById(R.id.ratingBar_locationItem_starRating)
            layout = itemView.findViewById(R.id.layout_locationItem)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_location_item, parent, false)
        val holder = ViewHolder(view)
        return holder
    }

    override fun getItemCount(): Int {
        return locationList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val location = locationList[position]
        val context = holder.layout.context
        holder.textViewName.text = location.name
        // TODO: format the money nicely to show it like $5.99
        holder.textViewMoneySpent.text = "Money Spent: $" + location.moneySpent.toString()
        // TODO: verify this works in displaying the emoji
        holder.textViewEmotion.text = try {
            LocationEntry.EMOJI.valueOf(location.emotion).emoji
        } catch (ex: IllegalArgumentException) {
            "¯\\_(ツ)_/¯"
        }
        holder.ratingBar.rating = location.rating.toFloat()
        holder.layout.isLongClickable = true
        holder.layout.setOnLongClickListener {
            // the holder.textViewBorrower is the textView that the PopMenu will be anchored to
            val popMenu = PopupMenu(context, holder.textViewName)
            popMenu.inflate(R.menu.menu_location_list_context)
            popMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_location_delete -> {
                        deleteFromBackendless(position)
                        true
                    }

                    else -> true
                }
            }
            popMenu.show()
            true

        }
        holder.layout.setOnClickListener{
            val context = holder.layout.context
            val detailIntent = Intent(context, LocationDetailActivity::class.java)
            detailIntent.putExtra(LocationDetailActivity.EXTRA_LOCATION_ENTRY, locationList[position])
            context.startActivity(detailIntent)
        }
    }
    private fun deleteFromBackendless(position: Int) {
        Log.d("LocationAdapter", "deleteFromBackendless: Trying to delete ${locationList[position]}")
        Backendless.Data.of(LocationEntry::class.java).save(locationList[position], object : AsyncCallback<LocationEntry> {
            override fun handleResponse(savedLocationEntry: LocationEntry) {
                Backendless.Data.of(LocationEntry::class.java).remove(savedLocationEntry, object : AsyncCallback<Long> {
                    override fun handleResponse(response: Long) {
                        // Contact has been deleted. The response is the
                        // time in milliseconds when the object was deleted
                        locationList.remove(locationList[position])
                        notifyDataSetChanged()
                    }

                    override fun handleFault(fault: BackendlessFault) {
                        // an error has occurred, the error code can be
                        // retrieved with fault.code
                    }
                })
            }

            override fun handleFault(fault: BackendlessFault) {
                // an error has occurred, the error code can be retrieved with fault.code
            }
        })
    }
}