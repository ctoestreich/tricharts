databaseChangeLog = {

	changeSet(author: "coestre (generated)", id: "1348848599557-1") {
		dropColumn(columnName: "version", tableName: "job_log")
	}
}
