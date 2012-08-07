package com.tgid.tri.common

import org.joda.time.format.PeriodFormatter
import org.joda.time.format.PeriodFormatterBuilder

/**
 */
class JodaTimeHelper {

    static PeriodFormatter getPeriodFormat(showHours = false, showMinutes = true, showSeconds = true) {
        def formatter = new PeriodFormatterBuilder()
        if(showHours) {
            formatter.minimumPrintedDigits(1).printZeroAlways().appendHours()
        }
        if(showMinutes) {
            if(showHours) {formatter.appendLiteral(':')}
            formatter.minimumPrintedDigits(2).printZeroAlways().appendMinutes()
        }
        if(showSeconds) {
            if(showMinutes) {formatter.appendLiteral(':')}
            formatter.minimumPrintedDigits(2).printZeroAlways().appendSeconds()
        }
        formatter.toFormatter()
    }
}
