#!/bin/bash

database_file_name="database.sql"

add_script() {
    cat $1 >> $database_file_name
    echo "" >> $database_file_name
}

# clear file if exists
> $database_file_name

# data types
add_script ./sql/data_types.sql

# functions
add_script ./sql/functions.sql

# tables
add_script ./sql/users.sql
add_script ./sql/licences.sql

# privileges
add_script ./sql/privileges.sql