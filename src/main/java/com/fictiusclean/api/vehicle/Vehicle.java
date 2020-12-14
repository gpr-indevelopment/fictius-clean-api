package com.fictiusclean.api.vehicle;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
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

    @NotBlank
    private String name;

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @Past
    @NotNull
    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDate manufacturingDate;

    @NotNull
    @Positive
    private BigDecimal cityGasLiterPerKilometerRate;

    @NotNull
    @Positive
    private BigDecimal highwayGasLiterPerKilometerRate;
}
