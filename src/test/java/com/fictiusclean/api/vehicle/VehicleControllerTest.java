package com.fictiusclean.api.vehicle;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fictiusclean.api.vehicle.exception.VehicleNotFoundException;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VehicleControllerTest {

    private final VehicleService vehicleService;

    private final MockMvc mvc;

    private final ObjectMapper objectMapper;

    public VehicleControllerTest() {
        vehicleService = mock(VehicleService.class);
        VehicleController vehicleController = new VehicleController(vehicleService);
        mvc = MockMvcBuilders.standaloneSetup(vehicleController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void create_validVehicleJson_creates() throws Exception {
        // given
        Vehicle vehicle = Vehicle
                .builder()
                .brand("FIAT")
                .name("Palio")
                .model("Weekend")
                .manufacturingDate(LocalDate.of(2012, 3, 5))
                .cityGasLiterPerKilometerRate(new BigDecimal("1.2345"))
                .highwayGasLiterPerKilometerRate(new BigDecimal("1.2345"))
                .build();
        // when
        ArgumentCaptor<Vehicle> vehicleCaptor = ArgumentCaptor.forClass(Vehicle.class);
        when(vehicleService.create(vehicleCaptor.capture())).thenReturn(Vehicle.builder().id(1L).build());
        // then
        MockHttpServletResponse response = mvc.perform(post(VehicleController.ROOT).contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(vehicle)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse();
        Vehicle responseVehicle = objectMapper.readValue(response.getContentAsString(), Vehicle.class);
        assertThat(responseVehicle.getId()).isEqualTo(1);
        assertThat(response.getHeader("Location")).contains("/api/vehicle/1");
        Vehicle createdVehicle = vehicleCaptor.getValue();
        assertThat(createdVehicle).usingRecursiveComparison().isEqualTo(vehicle);
        verify(vehicleService).create(any(Vehicle.class));
    }

    @Test
    public void create_noRequestBody_badRequest() throws Exception {
        mvc.perform(post(VehicleController.ROOT))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();
    }

    @Test
    public void findById_vehicleExists_finds() throws Exception {
        // given
        Long id = 1L;
        Vehicle expectedVehicle = new Vehicle();
        String url = String.format("%s/%d", VehicleController.ROOT, id);
        // when
        when(vehicleService.findById(id)).thenReturn(expectedVehicle);
        // then
        MockHttpServletResponse response = mvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        Vehicle actualVehicle = objectMapper.readValue(response.getContentAsString(), Vehicle.class);
        assertThat(actualVehicle).isEqualTo(expectedVehicle);
        verify(vehicleService).findById(id);
    }

    @Test
    public void findById_vehicleDoesNotExist_notFound() throws Exception {
        // given
        Long id = 1L;
        String url = String.format("%s/%d", VehicleController.ROOT, id);
        // when
        when(vehicleService.findById(id)).thenThrow(new VehicleNotFoundException());
        // then
        Exception exception = mvc.perform(get(url))
                .andExpect(status().isNotFound()).andReturn().getResolvedException();
        assertThat(exception).isInstanceOf(VehicleNotFoundException.class);
        verify(vehicleService).findById(id);
    }

    @Test
    public void findAll_vehiclesExist_finds() throws Exception {
        // given
        Vehicle expectedVehicle = new Vehicle();
        // when
        when(vehicleService.findAll()).thenReturn(Collections.singletonList(expectedVehicle));
        // then
        MockHttpServletResponse response = mvc.perform(get(VehicleController.ROOT))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        TypeReference<List<Vehicle>> typeRef = new TypeReference<>() {
        };
        List<Vehicle> actualList = objectMapper.readValue(response.getContentAsString(), typeRef);
        assertThat(actualList).hasSize(1);
        assertThat(actualList.get(0)).isEqualTo(expectedVehicle);
        verify(vehicleService).findAll();
    }

    @Test
    public void findAll_noVehicles_findsEmptyList() throws Exception {
        // when
        when(vehicleService.findAll()).thenReturn(Lists.emptyList());
        // then
        MockHttpServletResponse response = mvc.perform(get(VehicleController.ROOT))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        TypeReference<List<Vehicle>> typeRef = new TypeReference<>() {
        };
        List<Vehicle> actualList = objectMapper.readValue(response.getContentAsString(), typeRef);
        assertThat(actualList).hasSize(0);
        verify(vehicleService).findAll();
    }

    @Test
    public void update_validVehicle_updates() throws Exception {
        // given
        Vehicle expectedVehicle = new Vehicle();
        // when
        when(vehicleService.update(expectedVehicle)).thenReturn(expectedVehicle);
        // then
        MockHttpServletResponse response = mvc.perform(put(VehicleController.ROOT).contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(expectedVehicle)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        Vehicle actualVehicle = objectMapper.readValue(response.getContentAsString(), Vehicle.class);
        assertThat(actualVehicle).isEqualTo(expectedVehicle);
        verify(vehicleService).update(expectedVehicle);
    }

    @Test
    public void delete_validId_deletes() throws Exception {
        // given
        Long id = 1L;
        String url = String.format("%s/%d", VehicleController.ROOT, id);
        // when
        doNothing().when(vehicleService).delete(id);
        // then
        mvc.perform(delete(url))
                .andExpect(status().isNoContent())
                .andReturn()
                .getResponse();
        verify(vehicleService).delete(id);
    }
}
