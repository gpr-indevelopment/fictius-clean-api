package com.fictiusclean.api.vehicle.expenses;

import com.fictiusclean.api.vehicle.Vehicle;
import com.fictiusclean.api.vehicle.VehicleService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VehicleExpensesServiceTest {

    private final VehicleExpensesService vehicleExpensesService;

    private final VehicleService vehicleService;

    public VehicleExpensesServiceTest() {
        vehicleService = mock(VehicleService.class);
        this.vehicleExpensesService = new VehicleExpensesService(vehicleService);
    }

    @Test
    public void getGasExpensesByVehicleId_validParameters_returnsExpense() {
        // given
        Long vehicleId = 1L;
        Vehicle expectedVehicle = Vehicle
                .builder()
                .cityGasLiterPerKilometerRate(new BigDecimal("13.87"))
                .highwayGasLiterPerKilometerRate(new BigDecimal("20.03"))
                .build();
        BigDecimal gasPrice = new BigDecimal("2.75");
        BigDecimal cityKilometers = new BigDecimal("50.03");
        BigDecimal highwayKilometers = new BigDecimal("143.876");
        // when
        when(vehicleService.findById(vehicleId)).thenReturn(expectedVehicle);
        // then
        VehicleGasExpense vehicleGasExpense = vehicleExpensesService.getGasExpensesByVehicleId(vehicleId, gasPrice, cityKilometers, highwayKilometers);
        assertThat(vehicleGasExpense.getVehicle()).isEqualTo(expectedVehicle);
        assertThat(vehicleGasExpense.getValue()).isNotNull();
        assertThat(vehicleGasExpense.getValue()).isPositive();
        verify(vehicleService).findById(vehicleId);
    }

    @Test
    public void getGasExpenses_validParameters_returnsSortedExpenses() {
        // given
        BigDecimal gasPrice = new BigDecimal("2.75");
        BigDecimal cityKilometers = new BigDecimal("73.145");
        BigDecimal highwayKilometers = new BigDecimal("100.998");
        Vehicle vehicle1 = Vehicle.builder()
                .brand("FIAT")
                .highwayGasLiterPerKilometerRate(new BigDecimal("20.5"))
                .cityGasLiterPerKilometerRate(new BigDecimal("13.2"))
                .build();
        Vehicle vehicle2 = Vehicle.builder()
                .brand("CHEVROLET")
                .highwayGasLiterPerKilometerRate(new BigDecimal("15.2"))
                .cityGasLiterPerKilometerRate(new BigDecimal("9.3"))
                .build();
        Vehicle vehicle3 = Vehicle.builder()
                .brand("FERRARI")
                .highwayGasLiterPerKilometerRate(new BigDecimal("25.8"))
                .cityGasLiterPerKilometerRate(new BigDecimal("18.1"))
                .build();
        // when
        when(vehicleService.findAll()).thenReturn(Arrays.asList(vehicle3, vehicle2, vehicle1));
        // then
        List<VehicleGasExpense> gasExpenses = vehicleExpensesService.getRankedGasExpenses(gasPrice, cityKilometers, highwayKilometers);
        assertThat(gasExpenses).hasSize(3);
        assertThat(gasExpenses.get(0).getValue().compareTo(gasExpenses.get(1).getValue())).isEqualTo(1);
        assertThat(gasExpenses.get(1).getValue().compareTo(gasExpenses.get(2).getValue())).isEqualTo(1);
        verify(vehicleService).findAll();
    }
}
