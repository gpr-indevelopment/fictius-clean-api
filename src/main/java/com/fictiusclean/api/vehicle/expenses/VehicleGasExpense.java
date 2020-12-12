package com.fictiusclean.api.vehicle.expenses;

import com.fictiusclean.api.vehicle.Vehicle;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Getter
@Setter
public class VehicleGasExpense implements Comparable<VehicleGasExpense>{

    private BigDecimal value;

    private Vehicle vehicle;

    private BigDecimal gasPrice;

    private BigDecimal cityKilometers;

    private BigDecimal highwayKilometers;

    public VehicleGasExpense(Vehicle vehicle, BigDecimal gasPrice, BigDecimal cityKilometers, BigDecimal highwayKilometers) {
        this.vehicle = vehicle;
        this.gasPrice = gasPrice;
        this.cityKilometers = cityKilometers;
        this.highwayKilometers = highwayKilometers;
        this.value = calculateValue();
    }

    private BigDecimal calculateValue() {
        BigDecimal litersOnCity = cityKilometers.divide(vehicle.getCityGasLiterPerKilometerRate(), MathContext.DECIMAL128);
        BigDecimal litersOnHighway = highwayKilometers.divide(vehicle.getHighwayGasLiterPerKilometerRate(), MathContext.DECIMAL128);
        return gasPrice.multiply(litersOnCity.add(litersOnHighway), MathContext.DECIMAL128).setScale(15, RoundingMode.CEILING);
    }

    @Override
    public int compareTo(VehicleGasExpense o) {
        return -this.value.compareTo(o.value);
    }
}
