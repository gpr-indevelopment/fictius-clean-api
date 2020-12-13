package com.fictiusclean.api.vehicle.expenses;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fictiusclean.api.vehicle.Vehicle;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class VehicleGasExpenseTest {

    @Test
    public void new_expenseScenario_calculates() {
        // given
        BigDecimal gasPrice = new BigDecimal("2.577");
        BigDecimal cityKilometers = new BigDecimal("73.145");
        BigDecimal highwayKilometers = new BigDecimal("100.998");
        Vehicle vehicle = Vehicle
                .builder()
                .brand("FIAT")
                .highwayGasLiterPerKilometerRate(new BigDecimal("20.5"))
                .cityGasLiterPerKilometerRate(new BigDecimal("13.2"))
                .build();
        // then
        VehicleGasExpense expense = new VehicleGasExpense(vehicle, gasPrice, cityKilometers, highwayKilometers);
        assertThat(expense.getValue()).isEqualTo(new BigDecimal("26.976086473392462"));
    }

    @Test
    public void sorting_threeVehiclesInList_sortsByValueDesc() {
        // given
        BigDecimal cityKilometers = new BigDecimal("73.145");
        BigDecimal highwayKilometers = new BigDecimal("100.998");
        Vehicle vehicle = Vehicle.builder()
                .brand("FIAT")
                .highwayGasLiterPerKilometerRate(new BigDecimal("20.5"))
                .cityGasLiterPerKilometerRate(new BigDecimal("13.2"))
                .build();
        VehicleGasExpense expense1 = new VehicleGasExpense(vehicle, new BigDecimal("1.021"), cityKilometers, highwayKilometers);
        VehicleGasExpense expense2 = new VehicleGasExpense(vehicle, new BigDecimal("2.577"), cityKilometers, highwayKilometers);
        VehicleGasExpense expense3 = new VehicleGasExpense(vehicle, new BigDecimal("3.589"), cityKilometers, highwayKilometers);
        // then
        List<VehicleGasExpense> expenses = Arrays.asList(expense1, expense2, expense3);
        assertThat(expenses.get(0)).isEqualTo(expense1);
        assertThat(expenses.get(1)).isEqualTo(expense2);
        assertThat(expenses.get(2)).isEqualTo(expense3);
        Collections.sort(expenses);
        assertThat(expenses.get(0)).isEqualTo(expense3);
        assertThat(expenses.get(1)).isEqualTo(expense2);
        assertThat(expenses.get(2)).isEqualTo(expense1);
    }

    @Test
    public void serialization_populatedVehicleExpense_serializes() throws JsonProcessingException {
        // given
        BigDecimal cityKilometers = new BigDecimal("73.145");
        BigDecimal highwayKilometers = new BigDecimal("100.998");
        Vehicle vehicle = Vehicle.builder()
                .brand("FIAT")
                .highwayGasLiterPerKilometerRate(new BigDecimal("20.5"))
                .cityGasLiterPerKilometerRate(new BigDecimal("13.2"))
                .build();
        VehicleGasExpense expense = new VehicleGasExpense(vehicle, new BigDecimal("1.021"), cityKilometers, highwayKilometers);
        ObjectMapper objectMapper = new ObjectMapper();
        // then
        String expenseJson = objectMapper.writeValueAsString(expense);
        assertThat(expenseJson).isNotNull();
    }

    @Test
    public void new_nullGasPrice_throwsException() {
        assertThatThrownBy(() -> new VehicleGasExpense(new Vehicle(), null, new BigDecimal("20.5"), new BigDecimal("13.2")))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void new_nullVehicle_throwsException() {
        assertThatThrownBy(() -> new VehicleGasExpense(null, new BigDecimal("2.577"), new BigDecimal("20.5"), new BigDecimal("13.2")))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void new_nullKilometers_calculatesToZeroValue() {
        // given
        Vehicle vehicle = Vehicle.builder()
                .brand("FIAT")
                .highwayGasLiterPerKilometerRate(new BigDecimal("20.5"))
                .cityGasLiterPerKilometerRate(new BigDecimal("13.2"))
                .build();
        // then
        VehicleGasExpense vehicleGasExpense = new VehicleGasExpense(vehicle, new BigDecimal("2.577"), null, null);
        assertThat(vehicleGasExpense.getValue().compareTo(BigDecimal.ZERO)).isEqualTo(0);
    }
}
