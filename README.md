# M223 Punchclock Backend

This project was created as part of the Module 223 (ZLI) by Elia Schenker. It is the Backend of the Punchclock Project.

This project was originally forked from the git repository: https://github.com/zlict/m223-punchclock-quarkus

You can find the frontend written in Angular here: https://github.com/eliaSchenker/m223-punchclock-frontend

## Brief project description
An employee of a store chain (e.g. Coop, Migros) has the ability to record their work duration (as in old punch cards). Entries can be created, updated and deleted by employees. To better organize the entries certain categories can be added. An employee has to login into their personal account using a username and a password (authenticated using a JWT token). These user accounts are created by administrator. **Only the administrators** have the ability to create new accounts. As well as create new accounts the administrators also have the ability to manage a users location (e.g. the store they work at), update or delete the users and manage the available entry categories.

## First-time Authentication

Once the backend is started, a pre-created administrator account is available for use with the following credentials:<br>
Username: **Test-Admin**<br>
Password: **1234**<br>

## Quarkus Configuration & Execution

Following steps are required to run the project 
1. Make sure that OpenJDK 11 or higher is installed and that the JAVA_HOME environment variable is set correctly. 
2. Install Apache Maven 3.8.1 or higher
3. Change to the following path in the command line
`cd m223-punchclock-quarkus/`
4. Execute the following command to start the project
```shell script
./mvnw compile quarkus:dev
```

Following services are available to test the backend.

Swagger API: http://localhost:8080/q/swagger-ui/

H2 Console: http://localhost:8080/h2/ 
Datenquelle: jdbc:h2:mem:punchclock
Benutzername: zli
Passwort: zli