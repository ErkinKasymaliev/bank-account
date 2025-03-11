# REST API for managing users and their accounts in the banking system.

### Stack
1) Java 21
2) Spring boot 3
3) Postgres 16.1

### To run locally
1) Run ```./gradlew clean build``` to build the project
2) In terminal go to app directory ```cd app-directory``` and run ```docker-compose up```. App and db will be up in docker.
3) After app is up go to [Bank Account Swagger](localhost:9090/api/v1/swagger-ui.html)
4) To run tests ```./gradlew :test```