# yadisk - REST API сервис, который позволяет пользователям загружать и обновлять информацию о файлах и папках

## Настройка проекта

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

## Запуск проекта

`docker-compose build` - сборка образов  
`docker-compose up` - запуск контейнеров