#!/bin/bash
mvn clean
mvn compile -DskipTests=true -Paxis1-generate-sources
