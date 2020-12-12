package com.fictiusclean.api.vehicle;

import com.fictiusclean.api.vehicle.exception.InvalidVehicleCreationException;
import com.fictiusclean.api.vehicle.exception.InvalidVehicleUpdateException;
import com.fictiusclean.api.vehicle.exception.VehicleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public Vehicle create(Vehicle vehicle) {
        if(Objects.isNull(vehicle.getId())) {
            return vehicleRepository.save(vehicle);
        } else {
            String message = String.format("You cannot create a vehicle with a pre-existing ID: %d. Please consider an update instead.", vehicle.getId());
            throw new InvalidVehicleCreationException(message);
        }
    }

    public Vehicle findById(Long id) {
        return vehicleRepository.findById(id).orElseThrow(() -> new VehicleNotFoundException("A vehicle could not be found for ID: " + id));
    }

    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    public Vehicle update(Vehicle vehicle) {
        if(Objects.nonNull(vehicle.getId())) {
            return vehicleRepository.save(vehicle);
        } else {
            throw new InvalidVehicleUpdateException("The vehicle input for an update must have an ID.");
        }
    }

    public void delete(Long id) {
        if(vehicleRepository.existsById(id)) {
            vehicleRepository.deleteById(id);
        } else {
            String message = String.format("A vehicle could not be deleted for ID: %d. Not found.", id);
            throw new VehicleNotFoundException(message);
        }
    }
}
