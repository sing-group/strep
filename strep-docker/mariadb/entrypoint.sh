#!/bin/sh
# Initialise the data directory on first start (database and user from the
# environment) and then run MariaDB in the foreground.
set -eu

: "${MARIADB_DATABASE:?MARIADB_DATABASE is required}"
: "${MARIADB_USER:?MARIADB_USER is required}"
: "${MARIADB_PASSWORD:?MARIADB_PASSWORD is required}"
: "${MARIADB_ROOT_PASSWORD:?MARIADB_ROOT_PASSWORD is required}"

DATADIR=/var/lib/mysql

# Escape a value for use inside single quotes in SQL
sql_quote() {
    printf '%s' "$1" | sed "s/\\\\/\\\\\\\\/g; s/'/\\\\'/g"
}

if [ ! -d "$DATADIR/mysql" ]; then
    echo "[strep-mariadb] Initialising data directory"
    mariadb-install-db --user=mysql --datadir="$DATADIR" --skip-test-db > /dev/null

    mariadbd --user=mysql --datadir="$DATADIR" --skip-networking --socket=/run/mysqld/init.sock &
    pid=$!
    for _ in $(seq 30); do
        mariadb-admin --socket=/run/mysqld/init.sock ping --silent 2> /dev/null && break
        sleep 1
    done

    db=$(printf '%s' "$MARIADB_DATABASE" | sed 's/`/``/g')
    mariadb --socket=/run/mysqld/init.sock -uroot <<EOF
CREATE DATABASE IF NOT EXISTS \`$db\` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS '$(sql_quote "$MARIADB_USER")'@'%' IDENTIFIED BY '$(sql_quote "$MARIADB_PASSWORD")';
GRANT ALL PRIVILEGES ON \`$db\`.* TO '$(sql_quote "$MARIADB_USER")'@'%';
ALTER USER 'root'@'localhost' IDENTIFIED BY '$(sql_quote "$MARIADB_ROOT_PASSWORD")';
DELETE FROM mysql.global_priv WHERE User = '';
FLUSH PRIVILEGES;
EOF

    mariadb-admin --socket=/run/mysqld/init.sock -uroot -p"$MARIADB_ROOT_PASSWORD" shutdown
    wait "$pid"
    echo "[strep-mariadb] Database '$MARIADB_DATABASE' and user '$MARIADB_USER' created"
fi

exec mariadbd --user=mysql --datadir="$DATADIR"
