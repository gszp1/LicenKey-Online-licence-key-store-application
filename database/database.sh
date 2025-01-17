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
add_script ./sql/tables/platforms.sql
add_script ./sql/tables/licence_types.sql
add_script ./sql/tables/categories.sql
add_script ./sql/tables/users.sql
add_script ./sql/tables/publishers.sql
add_script ./sql/tables/services.sql
add_script ./sql/tables/licences.sql
add_script ./sql/tables/keys.sql

# privileges
add_script ./sql/privileges.sql