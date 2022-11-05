# yadisk is a REST API service that allows users to upload and update information about files and folders

## App settings

### App envs

```shell
SPRING_DATASOURCE_URL=jdbc:<database>://<host>:<port>/<dbname>
SPRING_DATASOURCE_USERNAME=<username>
SPRING_DATASOURCE_PASSWORD=<password>
SPRING_JPA_HIBERNATE_DDL_AUTO=<ddl_auto_type>
```

### DB envs

```shell
POSTGRES_DB: <database>
POSTGRES_USER: <username>
POSTGRES_PASSWORD: <password>
```

## Start app

`docker-compose build`
`docker-compose up`
