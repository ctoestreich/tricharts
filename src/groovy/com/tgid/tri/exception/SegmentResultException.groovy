package com.tgid.tri.exception

import com.tgid.tri.results.SegmentResult

/**
 * Created with IntelliJ IDEA.
 * User: coestre
 * Date: 8/7/12
 * Time: 10:43 AM
 * To change this template use File | Settings | File Templates.
 */
class SegmentResultException extends RuntimeException {
    String message
    SegmentResult problem
}
