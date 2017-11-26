package com.sofiwares.youtubesearcher


/**
 * Created by Ido Sofi on 11/25/2017.
 */
object DurationFormatter: IFormatter<String> {

    private val indexes = arrayOf("D", "H", "M", "S")

    override fun format(value: String): String {

        var parsedDuration = ""
        var time: String

        // The format prefix of ISO-8601 may vary between: PT#H#M#S / P#DT#H#M#S
        time = if (value.startsWith("PT"))
            value.substring(2)
        else
            value.substring(2).trim('T')

        for (i in indexes.indices) {
            val index = time.indexOf(indexes[i])
            if (index != -1) {
                val trimmedValue = time.substring(0, index)
                if (parsedDuration != "") {
                    parsedDuration += ":"
                }
                parsedDuration += String.format("%02d", Integer.parseInt(trimmedValue))
                time = time.substring(trimmedValue.length + 1)
            }
        }

        return if (parsedDuration.length == 2) "00:$parsedDuration" else parsedDuration
    }
}