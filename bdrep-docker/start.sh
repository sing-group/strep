#!/bin/bash

while /bin/true; do
    mysqld_safe --bind-address=0.0.0.0
done &
sleep 15

cd /root/bdrep_service
while /bin/true; do
    java -jar target/datasetservice-1.0.0-SNAPSHOT.jar
done &

cd /root/bdrep 
while /bin/true; do
    java -jar target/onlinepreprocessor-0.0.1-SNAPSHOT.jar
done

