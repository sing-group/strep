#!/bin/bash

mysqld_safe --bind-address=0.0.0.0 &
sleep 10
cd /root/bdrep 
java -jar target/onlinepreprocessor-0.0.1-SNAPSHOT.jar &
cd /root/bdrep_service
java -jar target/datasetservice-1.0.0-SNAPSHOT.jar