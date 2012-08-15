package com.tgid.tri

import com.tgid.tri.common.JodaTimeHelper
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.joda.time.Duration
import org.joda.time.DurationFieldType
import org.joda.time.Period
import org.joda.time.PeriodType
import org.joda.time.format.PeriodFormat
import org.joda.time.format.PeriodFormatter

import static org.joda.time.DurationFieldType.months
import static org.joda.time.DurationFieldType.years

class TriHarderTagLib {

    static namespace = "tri"

    def displayPace = {attrs ->
        def pace = attrs.pace
        def showAt = attrs?.showAt ?: true

        if(pace){
            out << "${showAt ? ' @ ' : ' '}$pace"
        }
    }

    def placement = { attrs, body ->
        def place = attrs.place
        def overall = attrs.overall
        def percentOnly = attrs?.percentOnly ?: false

        if(place && overall){
            def value = Math.round((place / overall) * 100 as float)

            def red = (188 * (value / 100))
            def green = 188 - red
            out << "<font style=\"color: rgb(${red.toInteger()}, ${green.toInteger()}, 0);\">"
            out << ((percentOnly) ? "${value}%" : "${place} (${value}%)")
            out << "</font>"
        } else {
            out << "N/A"
        }

    }

    def formatDuration = { attrs, body ->
        Duration duration = attrs.duration
        def defaultValue = attrs.defaultValue ?: "&nbsp;"
        def display = defaultValue

        if(duration) {
            PeriodFormatter periodFormatter = attrs.formatter ?: JodaTimeHelper.getPeriodFormat(duration.standardHours > 0, duration.standardMinutes > 0, duration.standardSeconds > 0)
            display = periodFormatter.print(duration.toPeriod())
        }
        out << display
    }

    def periodPicker = {attrs ->
        def name = attrs.name
        def id = attrs.id ?: name
        def value = attrs.value
        def prefix = attrs?.prefix ?: ''

        def periodType = getPeriodType(attrs.fields, DEFAULT_PERIOD_TYPE)

        if(value instanceof Duration) {
            value = value.toPeriod(periodType)
        }

        out << "<input type=\"hidden\" name=\"$prefix$name\" value=\"struct\" />"

        (0..<periodType.size()).each {i ->
            def fieldType = periodType.getFieldType(i)
            out << "<label for=\"${id}_${fieldType.name}\">"
            out << """<input type="text" name="${prefix}${name}_${fieldType.name}" id="${id}_${fieldType.name}" value="${value?.get(fieldType) ?: 0}" size="1"/>"""
            out << "&nbsp;" << message(code: "${DurationFieldType.name}.$fieldType.name", default: fieldType.name) << " "
            out << "</label>"
        }
    }

    def formatPeriod = {attrs ->
        def value = attrs.value
        if(!value) {
            throwTagError("'value' attribute is required")
        }

        def periodType = getPeriodType(attrs.fields, PeriodType.standard())

        if(value instanceof Duration) {
            value = value.toPeriod(periodType)
        } else {
            value = safeNormalize(value, periodType)
        }

        def formatter = PeriodFormat.default

        out << formatter.print(value)
    }

    private PeriodType getPeriodType(String fields, PeriodType defaultPeriodType) {
        PeriodType periodType
        if(fields) {
            periodType = getPeriodTypeForFields(fields)
        } else if(ConfigurationHolder.config?.jodatime?.periodpicker?.default?.fields) {
            periodType = getPeriodTypeForFields(ConfigurationHolder.config.jodatime.periodpicker.default.fields)
        } else {
            periodType = defaultPeriodType
        }
        return periodType
    }

    private static final PeriodType DEFAULT_PERIOD_TYPE = getPeriodTypeForFields("hours,minutes,seconds")

    private static PeriodType getPeriodTypeForFields(String fields) {
        def fieldTypes = fields.split(/\s*,\s*/).collect { DurationFieldType."$it"() } as DurationFieldType[]
        return PeriodType.forFields(fieldTypes)
    }

    /**
     * PeriodFormat.print will throw UnsupportedOperationException if years or months are present in period but
     * not supported by the formatter so we trim those fields off to avoid the problem.
     */
    private Period safeNormalize(Period value, PeriodType periodType) {
        if(!periodType.isSupported(years()) && years() in value.getFieldTypes()) {
            log.warn "Omitting years from value '$value' as format '$periodType' does not support years"
            value = value.withYears(0)
        }
        if(!periodType.isSupported(months()) && months() in value.getFieldTypes()) {
            log.warn "Omitting months from value '$value' as format '$periodType' does not support months"
            value = value.withMonths(0)
        }
        return value.normalizedStandard(periodType)
    }


}
