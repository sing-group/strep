#!/bin/bash

while /bin/true; do
    mysqld_safe --bind-address=0.0.0.0
done &
sleep 15

cd /root/strep_service
while /bin/true; do
    java -jar target/strep.service-1.0.0-SNAPSHOT.jar
done &

cd /root/strep 
while /bin/true; do
    java -jar target/strep-0.0.1-SNAPSHOT.jar
done
