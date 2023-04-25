# cards

Minimal [Spring Boot](http://projects.spring.io/spring-boot/) sample app.

## Requirements

For building and running the application you need:

- [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

## Installing dependencies and building the project locally

```shell
./mvnw clean install
```

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.logicea.cards.CardsApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
./mvnw spring-boot:run
```

## Usage:

```shell

# GET /api/cards?page=0&size=5
curl --location 'http://localhost:8080/api/cards' \
--header 'Authorization: Bearer {{token}}'
```

```shell
# GET /api/cards/{id}
curl --location 'http://localhost:8080/api/cards/9' \
--header 'Authorization: Bearer {{token}}'
```

```shell
# POST /api/cards
curl --location 'http://localhost:8080/api/cards' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer ' \
--data '{
  "name": "some card",
  "description": "some description",
  "color": "#FFFFFF"
}'
```

```shell
# PUT /api/cards
curl --location --request PUT 'http://localhost:8080/api/cards' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer {{token}}' \
--data '{
    "id": 8,
    "name": "some card EDITED",
    "description": "some description",
    "color": "#FFFFFF",
    "status": "DONE"
}'
```

## Requirements

1. Application users are identified uniquely by their mail address, have a role (Member or Admin) and use a password to authenticate themselves before accessing cards
   1. Members have access to cards they created
   1. Admins have access to all cards
1. A user creates a card by providing a name for it and, optionally, a description and a color
   1. Name is mandatory
   1. Color, if provided, should conform to a “6 alphanumeric characters prefixed with a #“ format
   1. Upon creation, the status of a card is To Do
1. A user can search through cards they have access to
   1. Filters include name, color, status and date of creation
   1. Optionally limit results using page & size or offset & limit options
   1. Results may be sorted by name, color, status, date of creation
1. A user can request a single card they have access to
1. A user can update the name, the description, the color and/or the status of a card they have access to
   1. Contents of the description and color fields can be cleared out
   1. Available statuses are To Do, In Progress and Done
1. A user can delete a card they have access to

## Pending requirements:

1. Allow admin to manage cards from any user
2. Allow sorting of GET /api/cards
3. Add login REST endpoint
4. Add password to application.properties and initialize it in InitializeAppConfig class

```shell
curl --location --request DELETE 'http://localhost:8080/api/cards/8' \
--header 'Authorization: Bearer {{token}}'
```
## License

This work is licensed under a
[Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License][cc-by-nc-sa].

[![CC BY-NC-SA 4.0][cc-by-nc-sa-image]][cc-by-nc-sa]

[cc-by-nc-sa]: http://creativecommons.org/licenses/by-nc-sa/4.0/
[cc-by-nc-sa-image]: https://licensebuttons.net/l/by-nc-sa/4.0/88x31.png
[cc-by-nc-sa-shield]: https://img.shields.io/badge/License-CC%20BY--NC--SA%204.0-lightgrey.svg