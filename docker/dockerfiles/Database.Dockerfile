FROM postgres:17

ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=rootroot

COPY ./docker/database/create-database.sql /docker-entrypoint-initdb.d/

EXPOSE 5432