package com.fictiusclean.api.vehicle.expenses;

import com.fictiusclean.api.vehicle.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleExpensesService {

    private final VehicleService vehicleService;

    public List<VehicleGasExpense> getRankedGasExpenses(BigDecimal gasPrice, BigDecimal cityKilometers, BigDecimal highwayKilometers) {
        return vehicleService
                .findAll()
                .stream()
                .map(vehicle -> new VehicleGasExpense(vehicle, gasPrice, cityKilometers, highwayKilometers))
                .sorted()
                .collect(Collectors.toList());
    }

    public VehicleGasExpense getGasExpensesByVehicleId(Long id, BigDecimal gasPrice, BigDecimal cityKilometers, BigDecimal highwayKilometers) {
        return new VehicleGasExpense(vehicleService.findById(id), gasPrice, cityKilometers, highwayKilometers);
    }
}
