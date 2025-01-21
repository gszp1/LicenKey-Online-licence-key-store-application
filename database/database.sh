#!/bin/bash

database_file_name="./sql/database.sql"

add_script() {
    cat $1 >> $database_file_name
    echo -e "\n" >> $database_file_name
}

# clear file if exists
> $database_file_name

# data types
# add_script ./sql/functionality/data_types.sql

# functions
add_script ./sql/functionality/functions.sql

# tables
add_script ./sql/tables/platforms.sql
add_script ./sql/tables/licence_types.sql
add_script ./sql/tables/categories.sql
add_script ./sql/tables/users.sql
add_script ./sql/tables/publishers.sql
add_script ./sql/tables/services.sql
add_script ./sql/tables/licences.sql
add_script ./sql/tables/orders.sql
add_script ./sql/tables/shopping_carts.sql
add_script ./sql/tables/keys.sql

# privileges
add_script ./sql/functionality/privileges.sql