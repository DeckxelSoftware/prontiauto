#!/bin/bash
echo $DDL_AUTO
java -Dspring.jpa.hibernate.ddl-auto=$DDL_AUTO -jar ./target/prontiauto8082.jar
