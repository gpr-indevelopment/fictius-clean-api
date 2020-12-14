# fictius-clean-api

## Local environment

The following commando will run local Docker container of the api on port 8080:
```console
$ docker container run -p 8080:8080 -d pimentgabriel/fictius-clean-api
```
For testing, you can use the `Fictius-Clean-API.postman_collection.json` export from Postman.

## Objective

Fictius-clean-api is an API for the purpose of managing `Vehicle` entities. It also has an URI for returning gas expenses based on the current gas price and kilometers traveled by the vehicles.

## CI/CD

This repository has a GitHub actions workflow that triggers with every commit to the "main" branch, and has the following steps:

1. JDK 11 setup.
2. Build with maven (automated tests run on this step).
3. Build and push Docker image to Docker Hub.

## API documentation

You can access the Swagger UI on the URI `http://localhost:8080/swagger-ui/index.html`.
The Swagger API JSON docs can be found on `http://localhost:8080/v2/api-docs`.
