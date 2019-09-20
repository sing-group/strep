FROM debian:9

RUN apt-get update && apt-get install -y \
      openjdk-8-jdk git maven

ADD debconf-mysql /root/debconf-mysql
ADD start.sh /root/start.sh

RUN chmod 755 /root/start.sh && cd && apt-get install -y wget lsb-release gnupg && \
    debconf-set-selections /root/debconf-mysql && export DEBIAN_FRONTEND=noninteractive && export DEBIAN_PRIORITY=critical && \
    wget http://repo.mysql.com/mysql-apt-config_0.8.13-1_all.deb && dpkg -i mysql-apt-config_0.8.13-1_all.deb && \
    apt-get update && apt-get install mysql-community-server -y

RUN mysqld_safe --bind-address=0.0.0.0 & sleep 10 && \
    mysqladmin create onlinepreprocessor && \
    echo "CREATE USER 'springuser'@'%' IDENTIFIED WITH mysql_native_password BY 'springpassword'; GRANT ALL PRIVILEGES on *.* TO 'springuser'@'%' WITH GRANT OPTION; flush privileges; " | mysql && \
    cd /root && \
    git clone https://github.com/sing-group/bdrep.git && \
    cd bdrep && \
    mvn clean package && mysqladmin shutdown && cd .. 

RUN cd /root && \
    git clone https://github.com/sing-group/bdrep_service.git && \
    cd bdrep_service && \
    mvn clean package && mkdir plugins && cp target/lib/nlpa* plugins/ && \
    cd plugins && jar xf nlpa-1.0.0.jar META-INF/services/org.bdp4j.pipe.Pipe && \
    sed -i -E "/^.*(NERFromStringBufferPipe|ComputePolarityFromStringBufferPipe|ComputePolarityTBWSFromStringBuffer|StoreTweetLangPipe)$/d" \
    META-INF/services/org.bdp4j.pipe.Pipe && jar uf nlpa-1.0.0.jar META-INF/services/org.bdp4j.pipe.Pipe && cd .. && \
    cd ..

ENTRYPOINT /root/start.sh

EXPOSE 8080