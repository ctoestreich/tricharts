package com.tgid.tri

import org.joda.time.Duration

import java.math.RoundingMode
import org.joda.time.format.PeriodFormatter
import org.joda.time.format.PeriodFormatterBuilder

class Segment {

    Duration duration
    SegmentType segmentType
    Float distance = 0.00

    static belongsTo = [race: Race]

    static constraints = {
        distance scale:2
    }

    transient Pace getPace() {
        def display = null, paceDuration = null, paceSpeed = null

        switch(this.segmentType) {
            case segmentType.Bike:
                println "distance $distance"
                println "speed : ${new BigDecimal(distance / (((duration.millis / 1000) / 60) / 60)).setScale(2, RoundingMode.HALF_UP)}"
                paceSpeed = new BigDecimal(distance / (((duration.millis / 1000) / 60) / 60)).setScale(2, RoundingMode.HALF_UP)
                display = "${paceSpeed} mph"
                break
            case segmentType.Run:
                println "distance $distance"
                println "speed : ${new BigDecimal(distance / (((duration.millis / 1000) / 60) / 60)).setScale(2, RoundingMode.HALF_UP)}"
                def runPace = new Duration(Math.round(duration.millis / distance))
                paceDuration = runPace
                PeriodFormatter formatter= new PeriodFormatterBuilder().appendMinutes().appendLiteral(':').minimumPrintedDigits(2).printZeroAlways().appendSeconds().toFormatter()
                display = formatter.print(runPace.toPeriod())
                break
            case segmentType.Swim:
                println "distance $distance"
                def runPace = Duration.standardSeconds(new Duration(Math.round(duration.millis / (distance*17.6))).standardSeconds)
                paceDuration = runPace
                PeriodFormatter formatter= new PeriodFormatterBuilder().appendMinutes().appendLiteral(':').minimumPrintedDigits(2).printZeroAlways().appendSeconds().toFormatter()
                display = formatter.print(runPace.toPeriod())

                break
            default:
                break
        }
        return new Pace(display: display, duration: paceDuration, speed: paceSpeed)
    }
}
