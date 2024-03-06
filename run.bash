#!/bin/bash
mvn clean package &&
    java -jar target/mailing-list-refactoring-1.0-SNAPSHOT-jar-with-dependencies.jar