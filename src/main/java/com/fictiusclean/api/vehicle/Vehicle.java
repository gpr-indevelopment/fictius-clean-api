package com.fictiusclean.api.vehicle;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String brand;

    private String model;

    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDate manufacturingDate;

    private BigDecimal cityGasLiterPerKilometerRate;

    private BigDecimal highwayGasLiterPerKilometerRate;
}
