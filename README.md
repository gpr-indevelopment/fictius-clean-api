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

## In-depth

The `VehicleController` class contains endpoints the CRUD operations.

1. POST to `/api/vehicle` creates a new `Vehicle`.
2. GET to `/api/vehicle/1` returns a JSON of the `Vehicle` with ID 1 in the database.
3. GET to `/api/vehicle` returns all `Vehicles` from the database. No pagination (since the database is in memory, and the API is meant to be simplified, I found no reason for having a paginated endpoint. Support to be added if needed).
4. PUT to `/api/vehicle/1` updates the `Vehicle` with ID 1.
5. DELETE to `/api/vehicle/1` deletes `Vehicle` with ID 1.

The `VehicleExpensesController` class contains endpoints for retrieving expenses of `Vehicles` based on a set of parameters:

1. GET to `/api/vehicle/expenses/gas` returns a ranked list (sorted by descending value) of `VehicleGasExpense` based on the `gasPrice`, `cityKilometers` and `highwayKilometers` that must be passed as query parameters.
2. GET to `/api/vehicle/1/expenses/gas` returns the `VehicleGasExpense` of `Vehicle` with ID 1 based on the same parameters of item 1.
