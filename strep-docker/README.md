# Deploying STRep with Docker on Debian 13

This directory runs STRep as three containers, all built on Debian 13 (trixie):

| Service | Image | Description |
|---|---|---|
| `web` | `Dockerfile.web` | Spring Boot web application (ports 8080 and 8443) |
| `service` | `Dockerfile.service` | Preprocessing service (BDP4J + NLPA) |
| `db` | `mariadb/Dockerfile` | MariaDB from the Debian repositories |

The images are built from the local source code. `web` uses this repository; `service`
uses the sibling checkouts of `bdp4j`, `nlpa` and `strep_service`, so the layout must be:

```
<workdir>/
├── bdp4j/
├── nlpa/
├── strep/            (this repository; compose.yaml is in strep/strep-docker)
└── strep_service/
```

Datasets, pipelines and generated CSV files live in named volumes shared by `web` and
`service`; the database lives in the `db-data` volume. Only `web` publishes ports.

## 1. Install Docker

```
sudo apt-get update
sudo apt-get install docker.io docker-compose docker-buildx
sudo usermod -aG docker "$USER"    # log out and in again
```

(Docker CE from download.docker.com with the `docker-compose-plugin` package works too.)
Compose 2.17 or later is needed for `additional_contexts`.

## 2. Create the TLS keystore

The application serves HTTPS on 8443 with a PKCS12 keystore whose key alias is `tomcat`.
For a self-signed certificate:

```
cd strep/strep-docker
mkdir -p secrets
keytool -genkeypair -alias tomcat -keyalg RSA -keysize 3072 -validity 3650 \
        -storetype PKCS12 -keystore secrets/keystore.p12 -dname "CN=your.host.name"
chmod 644 secrets/keystore.p12   # the containers run as uid 10001
```

(`keytool` comes with any JDK, e.g. `sudo apt-get install openjdk-21-jre-headless`.)
For a real certificate, convert it with
`openssl pkcs12 -export -in cert.pem -inkey key.pem -name tomcat -out secrets/keystore.p12`.

## 3. Configure

```
cp .env.example .env
chmod 600 .env
```

and fill in at least `STREP_DB_PASSWORD`, `STREP_DB_ROOT_PASSWORD`,
`STREP_KEYSTORE_PASSWORD` and the mail account. `.env` and `secrets/` are ignored by git.

NLPA settings (`config/`): the image uses `configurations.ini`, `babelfy.var.properties`
and `babelnet.var.properties` if they exist in `strep-docker/config` (git-ignored) and the
`.example` files otherwise. Put the API keys there before building.

## 4. Build and start

```
docker compose up -d --build
docker compose ps          # wait until the three services are "healthy"
docker compose logs -f web
```

The first start creates the database and user (MariaDB), then the web application
creates the schema and loads `data.sql`; the service starts once the web application is
healthy. Open https://localhost:8443/ (or http://localhost:8080/).

## Operations

* Update after pulling new code: `docker compose up -d --build`
* Stop: `docker compose down` (add `-v` to also delete the volumes and all data)
* Database backup: `docker compose exec db sh -c 'mariadb-dump -uroot -p"$MARIADB_ROOT_PASSWORD" "$MARIADB_DATABASE"' > strep.sql`
