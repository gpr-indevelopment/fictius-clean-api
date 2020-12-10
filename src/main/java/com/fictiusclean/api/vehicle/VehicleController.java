package com.fictiusclean.api.vehicle;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(VehicleController.ROOT)
@RequiredArgsConstructor
public class VehicleController {

    public static final String ROOT = "/api/vehicle";

    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<Vehicle> create(@RequestBody Vehicle vehicle) {
        Vehicle createdVehicle = vehicleService.create(vehicle);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdVehicle.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdVehicle);
    }

    @GetMapping("/{id}")
    public Vehicle find(@PathVariable Long id) {
        return vehicleService.find(id);
    }

    @GetMapping
    public List<Vehicle> findAll() {
        return vehicleService.findAll();
    }

    @PutMapping
    public Vehicle update(@RequestBody Vehicle vehicle) {
        return vehicleService.update(vehicle);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        vehicleService.delete(id);
    }
}
