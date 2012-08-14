package com.tgid.tri.race

import org.joda.time.Duration

/**
 */
class Pace implements Serializable {
    Duration duration
    String display = ''
    BigDecimal speed

    @Override
    String toString() {
        display
    }
}
