# Use the official PostgreSQL image from Docker Hub
FROM postgres:latest

# Set environment variables
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=1234
ENV POSTGRES_DB=postgres

# Copy SQL scripts to initialize the database
COPY ../src/main/resources/init.sql /docker-entrypoint-initdb.d/

