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

# GET /api/cards
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
  "color": "red",
  "status": "TO_DO"
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
    "color": "red",
    "status": "TO_DO"
}'
```

## Pending requirements:

- Allow admin to manage cards from any user
- 

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