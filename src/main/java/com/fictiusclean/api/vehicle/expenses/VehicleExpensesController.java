package com.fictiusclean.api.vehicle.expenses;

import com.fictiusclean.api.vehicle.VehicleController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class VehicleExpensesController {

    private final VehicleExpensesService vehicleExpensesService;

    @GetMapping(VehicleController.ROOT + "/expenses/gas")
    public List<VehicleGasExpense> getRankedGasExpenses(@RequestParam BigDecimal gasPrice,
                                               @RequestParam(required = false) BigDecimal cityKilometers,
                                               @RequestParam(required = false) BigDecimal highwayKilometers) {
        return vehicleExpensesService.getRankedGasExpenses(gasPrice, cityKilometers, highwayKilometers);
    }

    @GetMapping(VehicleController.ROOT + "/{vehicleId}/expenses/gas")
    public VehicleGasExpense getGasExpensesByVehicleId(@PathVariable Long vehicleId,
                                                      @RequestParam BigDecimal gasPrice,
                                                      @RequestParam(required = false) BigDecimal cityKilometers,
                                                      @RequestParam(required = false) BigDecimal highwayKilometers) {
        return vehicleExpensesService.getGasExpensesByVehicleId(vehicleId, gasPrice, cityKilometers, highwayKilometers);
    }
}
