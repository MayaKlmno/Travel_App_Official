package com.example.travelappofficial

import java.util.Date
import android.os.Parcelable
import android.widget.RatingBar
import kotlinx.parcelize.Parcelize


@Parcelize
data class LocationEntry(
    var name: String = "Random Location",
    var location: String = "City, Country",
    var rating: Int = 0,
    var moneySpent: Int = 0, // stored in cents (saves the decimal imprecision nonsense)
    var emotion: String = EMOJI.MOUNTAIN.name,
    var description: String = "Random Desc",
    var ownerId: String? = null,
    var objectId: String? = null // null so that new objects receive an id from the server
): Parcelable {
    enum class EMOJI(var emoji: String) {
        MOUNTAIN ("\uD83C\uDFD4"),
        TROPICAL ("\uD83C\uDFDD"),
        CITY("\uD83C\uDFD9"),
        MYSTICAL("\uD83C\uDFEF"),
        CASTLE("\uDBB9\uDCBF"),
        CAMPING("\uD83C\uDFD5"),
        DESERT("\uD83C\uDFDC")
    }
}
