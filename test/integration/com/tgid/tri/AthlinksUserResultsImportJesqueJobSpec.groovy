package com.tgid.tri



import net.greghaines.jesque.meta.dao.QueueInfoDAO
import net.greghaines.jesque.meta.dao.FailureDAO
import grails.plugin.spock.IntegrationSpec

class AthlinksUserResultsImportJesqueJobSpec extends IntegrationSpec {

    def jesqueService
    QueueInfoDAO queueInfoDao
    FailureDAO failureDao

    def "test AthlinksUserResultsImportJobSpec simple functionality"() {
        given:
        def queueName = "AthlinksUserResultsImportJobSpecQueue"

        when:
        // TODO: add when

        then:
        true
    }
}