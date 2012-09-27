package com.tgid.tri.data

class ImportLoggingService {

    def save(ImportLog importLog) {
        try {
            ImportLog.withNewTransaction {
                importLog.save()
            }
        } catch(Exception e) {
            log.error e
        }
    }
}
