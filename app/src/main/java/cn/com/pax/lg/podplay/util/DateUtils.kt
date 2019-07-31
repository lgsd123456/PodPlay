package cn.com.pax.lg.podplay.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun jsonDateToShortDate(jsonDate: String?): String {
//1
        if (jsonDate == null) {
            return "-"
        }
// 2
        val inFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
// 3
        val date = inFormat.parse(jsonDate)
// 4
        val outputFormat = DateFormat.getDateInstance(DateFormat.SHORT,
            Locale.getDefault())
// 6
        return outputFormat.format(date)
    }
}