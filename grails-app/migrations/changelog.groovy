databaseChangeLog = {

	changeSet(author: "coestre (generated)", id: "1348847409353-1") {
		createTable(tableName: "country") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "countryid", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "countryid3", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "country_name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-2") {
		createTable(tableName: "course_pattern") {
			column(name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "VARCHAR(350)") {
				constraints(nullable: "false")
			}

			column(name: "parentid", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "weight", type: "BIGINT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-3") {
		createTable(tableName: "course_pattern_local") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "distance", type: "DOUBLE") {
				constraints(nullable: "false")
			}

			column(name: "distance_type", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "race_category_type", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-4") {
		createTable(tableName: "course_pattern_local_map") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "course_pattern_local_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "map_key", type: "VARCHAR(300)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-5") {
		createTable(tableName: "facebook_user") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "access_token", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "uid", type: "BIGINT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-6") {
		createTable(tableName: "import_log") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "complete", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "create_date", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "VARCHAR(1000)")

			column(name: "error", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "import_name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-7") {
		createTable(tableName: "job_log") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "complete", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "create_date", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "VARCHAR(255)")

			column(name: "job_name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-8") {
		createTable(tableName: "password_code") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "date_fulfilled", type: "DATETIME")

			column(name: "token", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "username", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-9") {
		createTable(tableName: "race") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "athlink_raceid", type: "BIGINT")

			column(name: "course_pattern_id", type: "BIGINT")

			column(name: "date", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "distance", type: "FLOAT") {
				constraints(nullable: "false")
			}

			column(name: "distance_type", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "event_courseid", type: "BIGINT")

			column(name: "name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "race_category_id", type: "BIGINT")

			column(name: "race_category_type", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "race_type", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "results_url", type: "VARCHAR(255)")

			column(name: "status_type", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "city", type: "VARCHAR(255)")

			column(name: "country_id", type: "BIGINT")

			column(name: "courseid", type: "BIGINT")

			column(name: "state_id", type: "BIGINT")
		}
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-10") {
		createTable(tableName: "race_category") {
			column(name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "VARCHAR(350)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-11") {
		createTable(tableName: "race_result") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "athlink_entryid", type: "BIGINT")

			column(name: "duration", type: "VARCHAR(255)")

			column(name: "participants_age_group", type: "INT")

			column(name: "participants_gender", type: "INT")

			column(name: "participants_overall", type: "INT")

			column(name: "place_age_group", type: "INT")

			column(name: "place_gender", type: "INT")

			column(name: "place_overall", type: "INT")

			column(name: "race_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "user_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "age", type: "INT")

			column(name: "age_group", type: "VARCHAR(255)")

			column(name: "bib_number", type: "VARCHAR(255)")

			column(name: "gender_type", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-12") {
		createTable(tableName: "race_segment") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "race_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "segment_id", type: "BIGINT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-13") {
		createTable(tableName: "racer") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "last_import", type: "DATETIME")

			column(name: "racerid", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "user_id", type: "BIGINT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-14") {
		createTable(tableName: "registration_code") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "token", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "username", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "date_fulfilled", type: "DATETIME")
		}
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-15") {
		createTable(tableName: "results_import") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "job_status", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "race_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "records_errored", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "records_processed", type: "BIGINT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-16") {
		createTable(tableName: "role") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "authority", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-17") {
		createTable(tableName: "segment") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "distance", type: "FLOAT") {
				constraints(nullable: "false")
			}

			column(name: "distance_type", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "segment_type", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-18") {
		createTable(tableName: "segment_result") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "duration", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "place_age_group", type: "INT")

			column(name: "place_gender", type: "INT")

			column(name: "place_overall", type: "INT")

			column(name: "race_result_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "race_segment_id", type: "BIGINT")
		}
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-19") {
		createTable(tableName: "state") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "abbrev", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "countryid", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "provid", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-20") {
		createTable(tableName: "user") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "account_expired", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "account_locked", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "dob", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "enabled", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "first_name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "password", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "password_expired", type: "BIT") {
				constraints(nullable: "false")
			}

			column(name: "username", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "gender_type", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-21") {
		createTable(tableName: "user_race") {
			column(name: "user_races_id", type: "BIGINT")

			column(name: "race_id", type: "BIGINT")
		}
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-22") {
		createTable(tableName: "user_role") {
			column(name: "role_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "user_id", type: "BIGINT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-23") {
		createTable(tableName: "user_state") {
			column(name: "user_states_id", type: "BIGINT")

			column(name: "state_id", type: "BIGINT")
		}
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-24") {
		addPrimaryKey(columnNames: "role_id, user_id", tableName: "user_role")
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-25") {
		addForeignKeyConstraint(baseColumnNames: "course_pattern_local_id", baseTableName: "course_pattern_local_map", baseTableSchemaName: "tricharts", constraintName: "FK8E69D1756BB68A45", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "course_pattern_local", referencedTableSchemaName: "tricharts", referencesUniqueColumn: "false")
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-26") {
		addForeignKeyConstraint(baseColumnNames: "country_id", baseTableName: "race", baseTableSchemaName: "tricharts", constraintName: "FK354AD157ACA96A", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "country", referencedTableSchemaName: "tricharts", referencesUniqueColumn: "false")
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-27") {
		addForeignKeyConstraint(baseColumnNames: "course_pattern_id", baseTableName: "race", baseTableSchemaName: "tricharts", constraintName: "FK354AD11910269C", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "course_pattern", referencedTableSchemaName: "tricharts", referencesUniqueColumn: "false")
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-28") {
		addForeignKeyConstraint(baseColumnNames: "race_category_id", baseTableName: "race", baseTableSchemaName: "tricharts", constraintName: "FK354AD1382E6A84", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "race_category", referencedTableSchemaName: "tricharts", referencesUniqueColumn: "false")
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-29") {
		addForeignKeyConstraint(baseColumnNames: "state_id", baseTableName: "race", baseTableSchemaName: "tricharts", constraintName: "FK354AD19DAA5F8A", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "state", referencedTableSchemaName: "tricharts", referencesUniqueColumn: "false")
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-30") {
		addForeignKeyConstraint(baseColumnNames: "race_id", baseTableName: "race_result", baseTableSchemaName: "tricharts", constraintName: "FK99B6DD4B36BE721", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "race", referencedTableSchemaName: "tricharts", referencesUniqueColumn: "false")
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-31") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "race_result", baseTableSchemaName: "tricharts", constraintName: "FK99B6DD4B8E6E286A", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "user", referencedTableSchemaName: "tricharts", referencesUniqueColumn: "false")
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-32") {
		addForeignKeyConstraint(baseColumnNames: "race_id", baseTableName: "race_segment", baseTableSchemaName: "tricharts", constraintName: "FKD15E2FC536BE721", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "race", referencedTableSchemaName: "tricharts", referencesUniqueColumn: "false")
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-33") {
		addForeignKeyConstraint(baseColumnNames: "segment_id", baseTableName: "race_segment", baseTableSchemaName: "tricharts", constraintName: "FKD15E2FC5FC5ADC73", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "segment", referencedTableSchemaName: "tricharts", referencesUniqueColumn: "false")
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-34") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "racer", baseTableSchemaName: "tricharts", constraintName: "FK6740FC18E6E286A", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "user", referencedTableSchemaName: "tricharts", referencesUniqueColumn: "false")
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-35") {
		addForeignKeyConstraint(baseColumnNames: "race_id", baseTableName: "results_import", baseTableSchemaName: "tricharts", constraintName: "FK72BEF40E36BE721", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "race", referencedTableSchemaName: "tricharts", referencesUniqueColumn: "false")
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-36") {
		addForeignKeyConstraint(baseColumnNames: "race_result_id", baseTableName: "segment_result", baseTableSchemaName: "tricharts", constraintName: "FKA05436A9BE80054B", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "race_result", referencedTableSchemaName: "tricharts", referencesUniqueColumn: "false")
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-37") {
		addForeignKeyConstraint(baseColumnNames: "race_segment_id", baseTableName: "segment_result", baseTableSchemaName: "tricharts", constraintName: "FKA05436A97291E610", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "race_segment", referencedTableSchemaName: "tricharts", referencesUniqueColumn: "false")
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-38") {
		addForeignKeyConstraint(baseColumnNames: "race_id", baseTableName: "user_race", baseTableSchemaName: "tricharts", constraintName: "FK143BBEC536BE721", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "race", referencedTableSchemaName: "tricharts", referencesUniqueColumn: "false")
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-39") {
		addForeignKeyConstraint(baseColumnNames: "user_races_id", baseTableName: "user_race", baseTableSchemaName: "tricharts", constraintName: "FK143BBEC5966DC707", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "user", referencedTableSchemaName: "tricharts", referencesUniqueColumn: "false")
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-40") {
		addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "user_role", baseTableSchemaName: "tricharts", constraintName: "FK143BF46AE943648A", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "role", referencedTableSchemaName: "tricharts", referencesUniqueColumn: "false")
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-41") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "user_role", baseTableSchemaName: "tricharts", constraintName: "FK143BF46A8E6E286A", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "user", referencedTableSchemaName: "tricharts", referencesUniqueColumn: "false")
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-42") {
		addForeignKeyConstraint(baseColumnNames: "state_id", baseTableName: "user_state", baseTableSchemaName: "tricharts", constraintName: "FK7352CF1D9DAA5F8A", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "state", referencedTableSchemaName: "tricharts", referencesUniqueColumn: "false")
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-43") {
		addForeignKeyConstraint(baseColumnNames: "user_states_id", baseTableName: "user_state", baseTableSchemaName: "tricharts", constraintName: "FK7352CF1D7854EB5F", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "user", referencedTableSchemaName: "tricharts", referencesUniqueColumn: "false")
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-44") {
		createIndex(indexName: "CoursePatternMapKey_Index", tableName: "course_pattern_local_map", unique: "false") {
			column(name: "map_key")
		}
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-45") {
		createIndex(indexName: "map_key", tableName: "course_pattern_local_map", unique: "true") {
			column(name: "map_key")
		}
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-46") {
		createIndex(indexName: "uid", tableName: "facebook_user", unique: "true") {
			column(name: "uid")
		}
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-47") {
		createIndex(indexName: "UserNamePassword_Index", tableName: "password_code", unique: "false") {
			column(name: "username")
		}
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-48") {
		createIndex(indexName: "RaceCategory_Index", tableName: "race", unique: "false") {
			column(name: "race_category_type")
		}
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-49") {
		createIndex(indexName: "RaceType_Index", tableName: "race", unique: "false") {
			column(name: "race_type")
		}
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-50") {
		createIndex(indexName: "race_category_type", tableName: "race", unique: "true") {
			column(name: "race_category_type")

			column(name: "date")

			column(name: "name")
		}
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-51") {
		createIndex(indexName: "UserName_Index", tableName: "registration_code", unique: "false") {
			column(name: "username")
		}
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-52") {
		createIndex(indexName: "authority", tableName: "role", unique: "true") {
			column(name: "authority")
		}
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-53") {
		createIndex(indexName: "provid", tableName: "state", unique: "true") {
			column(name: "provid")
		}
	}

	changeSet(author: "coestre (generated)", id: "1348847409353-54") {
		createIndex(indexName: "username", tableName: "user", unique: "true") {
			column(name: "username")
		}
	}

	include file: 'update-float-column-types.groovy'
}
