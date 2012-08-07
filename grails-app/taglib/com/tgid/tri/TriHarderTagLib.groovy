package com.tgid.tri

import org.joda.time.Duration
import org.joda.time.format.PeriodFormatter
import com.tgid.tri.common.JodaTimeHelper

class TriHarderTagLib {

    static namespace = "tri"

    def formatDuration = { attrs, body ->
        Duration duration = attrs.duration
        def display = ""

        if(duration){
            PeriodFormatter periodFormatter = attrs.formatter ?: JodaTimeHelper.getPeriodFormat(duration.standardHours > 0, duration.standardMinutes > 0, duration.standardSeconds > 0)
            display = periodFormatter.print(duration.toPeriod())
        }
        out << display
    }


}
