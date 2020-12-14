# fictius-clean-api

## Local environment

You can run a local Docker container of the api by using the following command:
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
