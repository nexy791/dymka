package com.ribsky.data.utils.date

class DateUtils {

    companion object {

        fun currentDayInDDMMYYYY(): String {
            val date = System.currentTimeMillis()
            val day = date / 1000 / 60 / 60 / 24
            val month = date / 1000 / 60 / 60 / 24 / 30
            val year = date / 1000 / 60 / 60 / 24 / 30 / 12
            return "$day.$month.$year"
        }
    }
}
