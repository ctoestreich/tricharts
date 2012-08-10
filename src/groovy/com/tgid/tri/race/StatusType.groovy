package com.tgid.tri.race

import groovy.util.logging.Commons

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType

@Commons
@XmlAccessorType(XmlAccessType.FIELD)
enum StatusType {
    Pending(2), Approved(1), Deleted(0)

    private final int value

    int value() { return value }

    StatusType(int value) {this.value = value}

    static StatusType getStatusType(String value) {
        try {
            getStatusType(Integer.parseInt(value))
        } catch (NumberFormatException e) {
            log.error e
            getStatusType(2)
        }
    }

    static StatusType getStatusType(int value) {
        StatusType.values().find { it.value == value }
    }


}
