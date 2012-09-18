package com.tgid.tri.ui

import javax.persistence.metamodel.ListAttribute

//        /**{
//         *     y: 55.11,
//         *     color: colors[0],
//         *     drilldown: {
//         *         name: 'MSIE versions',
//         *         categories: ['MSIE 6.0', 'MSIE 7.0', 'MSIE 8.0', 'MSIE 9.0'],
//         *         data: [10.85, 7.35, 33.06, 2.81],
//         *         color: colors[0]
//         *    }
//         * }
class BarChartSeriesData {
    String y = ""
    String color = ""
    Boolean hasData = false
    BarChartSeriesDrilldownData drilldown
}

class BarChartSeriesDrilldownData {
    String name = ""
    List<String> categories = []
    List data = []
    String color = ""
}
