package com.fictiusclean.api.vehicle;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class Vehicle {

    @Id
    private Long id;

    private String name;

    private String brand;

    private String model;

    private LocalDate manufacturingDate;

    private BigDecimal cityGasLiterPerKilometerRate;

    private BigDecimal highwayGasLiterPerKilometerRate;
}
