# MariaDB from Debian 13 (trixie) for STRep
FROM debian:trixie-slim
RUN apt-get update \
    && apt-get install -y --no-install-recommends mariadb-server \
    && rm -rf /var/lib/apt/lists/* \
    # The package initialises a data directory; the entrypoint creates its own
    && rm -rf /var/lib/mysql/* \
    && mkdir -p /run/mysqld \
    && chown mysql:mysql /run/mysqld /var/lib/mysql
COPY 50-strep.cnf /etc/mysql/mariadb.conf.d/50-strep.cnf
COPY entrypoint.sh /usr/local/bin/strep-mariadb-entrypoint
RUN chmod 755 /usr/local/bin/strep-mariadb-entrypoint
VOLUME /var/lib/mysql
EXPOSE 3306
USER mysql
HEALTHCHECK --interval=10s --timeout=5s --start-period=30s --retries=5 \
    CMD mariadb-admin --protocol=socket ping --silent || exit 1
ENTRYPOINT ["/usr/local/bin/strep-mariadb-entrypoint"]
