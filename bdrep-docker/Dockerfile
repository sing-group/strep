FROM debian:9

#username = admin y password = 12admin34.

#Install openjdk
RUN apt-get update && apt-get install -y \
      openjdk-8-jdk git maven

#Add the configuration for the mysql packages (debconf)
ADD debconf-mysql /root/debconf-mysql

#Add a start script for starting the container
ADD start.sh /root/start.sh

# Install mysql8.0
RUN chmod 755 /root/start.sh && cd && apt-get install -y wget lsb-release gnupg && \
    debconf-set-selections /root/debconf-mysql && export DEBIAN_FRONTEND=noninteractive && export DEBIAN_PRIORITY=critical && \
    wget http://repo.mysql.com/mysql-apt-config_0.8.13-1_all.deb && dpkg -i mysql-apt-config_0.8.13-1_all.deb && \
    apt-get update && apt-get install mysql-community-server -y

#Make input/output directories
RUN mkdir /root/datasets && mkdir /root/pipelines && mkdir /root/csv 

#Download && configure the software
RUN cd /root && git clone https://github.com/sing-group/bdrep.git && \
    sed -i.bak -e "s/^dataset\.storage.*/dataset.storage=\/root\/datasets\//g" /root/bdrep/src/main/resources/application.properties && \
    sed -i.bak -e "s/^pipeline\.storage.*/pipeline.storage=\/root\/pipelines\//g" /root/bdrep/src/main/resources/application.properties && \
    sed -i.bak -e "s/^csv\.storage.*/csv.storage=\/root\/csv\//g" /root/bdrep/src/main/resources/application.properties && \
    cd /root && git clone https://github.com/sing-group/bdrep_service.git && \
    sed -i.bak -e "s/^datasetStorage.*/datasetStorage=\/root\/datasets\//g" /root/bdrep_service/src/main/resources/service.properties && \
    sed -i.bak -e "s/^pipelineStorage.*/pipelineStorage=\/root\/pipelines\//g" /root/bdrep_service/src/main/resources/service.properties && \
    sed -i.bak -e "s/^outputStorage.*/outputStorage=\/root\/csv\//g" /root/bdrep_service/src/main/resources/service.properties

#Build bdrep
RUN mysqld_safe --bind-address=0.0.0.0 & sleep 15 && \
    mysqladmin create onlinepreprocessor && \
    echo "CREATE USER 'springuser'@'%' IDENTIFIED WITH mysql_native_password BY 'springpassword'; GRANT ALL PRIVILEGES on *.* TO 'springuser'@'%' WITH GRANT OPTION; flush privileges; " | mysql && \
    cd /root/bdrep && mvn clean package && mysqladmin shutdown && cd .. 

#Build bdrep_service
RUN cd /root/bdrep_service && \
    mvn clean package && mkdir plugins && cp target/lib/nlpa* plugins/ && \
    cd plugins && jar xf nlpa-1.0.0.jar META-INF/services/org.bdp4j.pipe.Pipe && \
    sed -i -E "/^.*(NERFromStringBufferPipe|ComputePolarityFromStringBufferPipe|ComputePolarityTBWSFromStringBuffer|StoreTweetLangPipe)$/d" \
    META-INF/services/org.bdp4j.pipe.Pipe && jar uf nlpa-1.0.0.jar META-INF/services/org.bdp4j.pipe.Pipe && cd .. && \
    cd ..

#Clean the maven library
RUN rm -rf /root/.m2/repository/*

ENTRYPOINT /root/start.sh

EXPOSE 8080 8443