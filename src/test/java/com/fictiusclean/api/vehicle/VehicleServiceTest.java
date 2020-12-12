package com.fictiusclean.api.vehicle;

import com.fictiusclean.api.vehicle.exception.InvalidVehicleCreationException;
import com.fictiusclean.api.vehicle.exception.InvalidVehicleUpdateException;
import com.fictiusclean.api.vehicle.exception.VehicleNotFoundException;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VehicleServiceTest {

    private final VehicleService vehicleService;

    private final VehicleRepository vehicleRepository;

    public VehicleServiceTest() {
        vehicleRepository = mock(VehicleRepository.class);
        vehicleService = new VehicleService(vehicleRepository);
    }

    @Test
    public void create_nullIdOnVehicle_creates() {
        // given
        Vehicle inputVehicle = new Vehicle();
        Vehicle expectedVehicle = Vehicle.builder().id(1L).build();
        // when
        when(vehicleRepository.save(inputVehicle)).thenReturn(expectedVehicle);
        // then
        Vehicle actualVehicle = vehicleService.create(inputVehicle);
        assertThat(actualVehicle).isEqualTo(expectedVehicle);
        verify(vehicleRepository).save(inputVehicle);
    }

    @Test
    public void create_inputVehicleHasId_throwsInvalidVehicleCreationException() {
        // given
        Vehicle inputVehicle = Vehicle.builder().id(1L).build();
        // then
        assertThatThrownBy(() -> vehicleService.create(inputVehicle)).isInstanceOf(InvalidVehicleCreationException.class);
    }

    @Test
    public void findById_vehicleFound_returnsFoundVehicle() {
        // given
        Long id = 1L;
        Vehicle expectedVehicle = new Vehicle();
        // when
        when(vehicleRepository.findById(id)).thenReturn(Optional.of(expectedVehicle));
        Vehicle actualVehicle = vehicleService.findById(id);
        assertThat(actualVehicle).isEqualTo(expectedVehicle);
        verify(vehicleRepository).findById(id);
    }

    @Test
    public void findById_vehicleNotFoundForId_throwsVehicleNotFoundException() {
        // given
        Long id = 1L;
        // when
        when(vehicleRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> vehicleService.findById(id)).isInstanceOf(VehicleNotFoundException.class);
        verify(vehicleRepository).findById(id);
    }

    @Test
    public void findAll_foundOneVehicle_returnsList() {
        // given
        Vehicle foundVehicle = new Vehicle();
        // when
        when(vehicleRepository.findAll()).thenReturn(Collections.singletonList(foundVehicle));
        // then
        List<Vehicle> foundVehicles = vehicleService.findAll();
        assertThat(foundVehicles).hasSize(1);
        assertThat(foundVehicles.get(0)).isEqualTo(foundVehicle);
        verify(vehicleRepository).findAll();
    }

    @Test
    public void findAll_foundNoVehicles_returnsEmptyList() {
        // given
        Vehicle foundVehicle = new Vehicle();
        // when
        when(vehicleRepository.findAll()).thenReturn(Lists.emptyList());
        // then
        List<Vehicle> foundVehicles = vehicleService.findAll();
        assertThat(foundVehicles).hasSize(0);
        verify(vehicleRepository).findAll();
    }

    @Test
    public void update_vehicleHasAnId_updates() {
        // given
        Vehicle inputVehicle = Vehicle.builder().id(1L).build();
        Vehicle expectedVehicle = new Vehicle();
        // when
        when(vehicleRepository.save(inputVehicle)).thenReturn(expectedVehicle);
        // then
        Vehicle actualVehicle = vehicleService.update(inputVehicle);
        assertThat(actualVehicle).isEqualTo(expectedVehicle);
        verify(vehicleRepository).save(inputVehicle);
    }

    @Test
    public void update_vehicleDoesNotHaveAnId_throwsInvalidVehicleUpdateException() {
        // given
        Vehicle inputVehicle = new Vehicle();
        // then
        assertThatThrownBy(() -> vehicleService.update(inputVehicle)).isInstanceOf(InvalidVehicleUpdateException.class);
    }

    @Test
    public void delete_validId_deletes() {
        // given
        Long id = 1L;
        // when
        when(vehicleRepository.existsById(id)).thenReturn(true);
        doNothing().when(vehicleRepository).deleteById(id);
        // then
        vehicleService.delete(id);
        verify(vehicleRepository).existsById(id);
        verify(vehicleRepository).deleteById(id);
    }

    @Test
    public void delete_vehicleDoesNotExistById_throwsVehicleNotFoundException() {
        // given
        Long id = 1L;
        // when
        when(vehicleRepository.existsById(id)).thenReturn(false);
        // then
        assertThatThrownBy(() -> vehicleService.delete(id)).isInstanceOf(VehicleNotFoundException.class);
        verify(vehicleRepository).existsById(id);
    }
}
