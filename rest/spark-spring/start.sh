#!/bin/sh
mvn clean install exec:java -DskipTests=true -Dspark-port=8080
