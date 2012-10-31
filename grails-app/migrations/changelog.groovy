databaseChangeLog = {

    include file: 'initial-database.groovy'

    include file: 'update-float-column-types.groovy'

    include file: 'add-user-sports-table.groovy'
}
