package com.fictiusclean.api.vehicle;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(notes = "Vehicle name. Must not be blank or null.")
    private String name;

    @NotBlank
    @ApiModelProperty(notes = "Vehicle brand. Must not be blank or null.")
    private String brand;

    @NotBlank
    @ApiModelProperty(notes = "Vehicle model. Must not be blank or null.")
    private String model;

    @Past
    @NotNull
    @JsonFormat(pattern = "MM-dd-yyyy")
    @ApiModelProperty(notes = "Date (MM-dd-yyyy) the vehicle was manufactured. Must be a past non-null date.")
    private LocalDate manufacturingDate;

    @NotNull
    @Positive
    @ApiModelProperty(notes = "Km/L Gas consumption on city. Must be a non-null positive number.")
    private BigDecimal cityGasLiterPerKilometerRate;

    @NotNull
    @Positive
    @ApiModelProperty(notes = "Km/L Gas consumption on highway. Must be a non-null positive number.")
    private BigDecimal highwayGasLiterPerKilometerRate;
}
