package com.tgid.tri.exception

import com.tgid.tri.results.RaceResult

/**
 * Created with IntelliJ IDEA.
 * User: coestre
 * Date: 8/7/12
 * Time: 10:44 AM
 * To change this template use File | Settings | File Templates.
 */
class RaceResultException extends RuntimeException {
    String message
    RaceResult problem
}
