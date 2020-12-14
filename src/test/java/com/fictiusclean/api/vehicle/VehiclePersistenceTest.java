package com.fictiusclean.api.vehicle;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class VehiclePersistenceTest {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Test
    public void save_vehicleWithNullName_throws() {
        // given
        Vehicle vehicle = Vehicle
                .builder()
                .brand("SOME_BRAND")
                .manufacturingDate(LocalDate.now())
                .highwayGasLiterPerKilometerRate(BigDecimal.TEN)
                .cityGasLiterPerKilometerRate(BigDecimal.TEN)
                .model("SOME_MODEL")
                .build();
        // then
        assertThatThrownBy(() -> vehicleRepository.saveAndFlush(vehicle)).isInstanceOf(ConstraintViolationException.class).hasMessageContaining("name");
    }

    @Test
    public void save_vehicleWithNullBrand_throws() {
        // given
        Vehicle vehicle = Vehicle
                .builder()
                .name("SOME_BRAND")
                .manufacturingDate(LocalDate.now())
                .highwayGasLiterPerKilometerRate(BigDecimal.TEN)
                .cityGasLiterPerKilometerRate(BigDecimal.TEN)
                .model("SOME_MODEL")
                .build();
        // then
        assertThatThrownBy(() -> vehicleRepository.saveAndFlush(vehicle)).isInstanceOf(ConstraintViolationException.class).hasMessageContaining("brand");
    }

    @Test
    public void save_vehicleWithNullModel_throws() {
        // given
        Vehicle vehicle = Vehicle
                .builder()
                .name("SOME_BRAND")
                .manufacturingDate(LocalDate.now())
                .highwayGasLiterPerKilometerRate(BigDecimal.TEN)
                .cityGasLiterPerKilometerRate(BigDecimal.TEN)
                .brand("SOME_MODEL")
                .build();
        // then
        assertThatThrownBy(() -> vehicleRepository.saveAndFlush(vehicle)).isInstanceOf(ConstraintViolationException.class).hasMessageContaining("model");
    }

    @Test
    public void save_vehicleWithFutureManufacturingDate_throws() {
        // given
        Vehicle vehicle = Vehicle
                .builder()
                .name("SOME_BRAND")
                .manufacturingDate(LocalDate.of(9999,1, 1))
                .highwayGasLiterPerKilometerRate(BigDecimal.TEN)
                .cityGasLiterPerKilometerRate(BigDecimal.TEN)
                .brand("SOME_MODEL")
                .model("SOME_MODEL")
                .build();
        // then
        assertThatThrownBy(() -> vehicleRepository.saveAndFlush(vehicle)).isInstanceOf(ConstraintViolationException.class).hasMessageContaining("past");
    }

    @Test
    public void save_vehicleWithNullManufacturingDate_throws() {
        // given
        Vehicle vehicle = Vehicle
                .builder()
                .name("SOME_BRAND")
                .highwayGasLiterPerKilometerRate(BigDecimal.TEN)
                .cityGasLiterPerKilometerRate(BigDecimal.TEN)
                .brand("SOME_MODEL")
                .model("SOME_MODEL")
                .build();
        // then
        assertThatThrownBy(() -> vehicleRepository.saveAndFlush(vehicle)).isInstanceOf(ConstraintViolationException.class).hasMessageContaining("manufacturingDate");
    }

    @Test
    public void save_vehicleWithNullCityGasLiterPerKilometerRate_throws() {
        // given
        Vehicle vehicle = Vehicle
                .builder()
                .name("SOME_BRAND")
                .highwayGasLiterPerKilometerRate(BigDecimal.TEN)
                .manufacturingDate(LocalDate.now())
                .brand("SOME_MODEL")
                .model("SOME_MODEL")
                .build();
        // then
        assertThatThrownBy(() -> vehicleRepository.saveAndFlush(vehicle)).isInstanceOf(ConstraintViolationException.class).hasMessageContaining("cityGasLiterPerKilometerRate");
    }

    @Test
    public void save_vehicleWithNullHighwayGasLiterPerKilometerRate_throws() {
        // given
        Vehicle vehicle = Vehicle
                .builder()
                .name("SOME_BRAND")
                .cityGasLiterPerKilometerRate(BigDecimal.TEN)
                .manufacturingDate(LocalDate.now())
                .brand("SOME_MODEL")
                .model("SOME_MODEL")
                .build();
        // then
        assertThatThrownBy(() -> vehicleRepository.saveAndFlush(vehicle)).isInstanceOf(ConstraintViolationException.class).hasMessageContaining("highwayGasLiterPerKilometerRate");
    }

    @Test
    public void save_vehicleWithNegativeCityGasLiterPerKilometerRate_throws() {
        // given
        Vehicle vehicle = Vehicle
                .builder()
                .name("SOME_BRAND")
                .highwayGasLiterPerKilometerRate(BigDecimal.TEN)
                .cityGasLiterPerKilometerRate(new BigDecimal("-10"))
                .manufacturingDate(LocalDate.now())
                .brand("SOME_MODEL")
                .model("SOME_MODEL")
                .build();
        // then
        assertThatThrownBy(() -> vehicleRepository.saveAndFlush(vehicle)).isInstanceOf(ConstraintViolationException.class).hasMessageContaining("greater than 0");
    }

    @Test
    public void save_vehicleWithNegativeHighwayGasLiterPerKilometerRate_throws() {
        // given
        Vehicle vehicle = Vehicle
                .builder()
                .name("SOME_BRAND")
                .highwayGasLiterPerKilometerRate(new BigDecimal("-10"))
                .cityGasLiterPerKilometerRate(BigDecimal.TEN)
                .manufacturingDate(LocalDate.now())
                .brand("SOME_MODEL")
                .model("SOME_MODEL")
                .build();
        // then
        assertThatThrownBy(() -> vehicleRepository.saveAndFlush(vehicle)).isInstanceOf(ConstraintViolationException.class).hasMessageContaining("greater than 0");
    }
}
