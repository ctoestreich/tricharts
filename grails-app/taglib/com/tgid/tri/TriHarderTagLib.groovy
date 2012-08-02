package com.tgid.tri

import org.joda.time.Duration
import org.joda.time.format.PeriodFormatter

class TriHarderTagLib {

    static namespace = "tri"

    def formatDuration = { attrs, body ->
        Duration duration = attrs.duration
        PeriodFormatter periodFormatter = attrs.formatter
        println duration.standardSeconds
        def display = periodFormatter.print(duration.toPeriod())
        out << display
    }


}
