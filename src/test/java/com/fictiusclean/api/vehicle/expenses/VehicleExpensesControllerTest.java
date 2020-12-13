package com.fictiusclean.api.vehicle.expenses;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fictiusclean.api.vehicle.Vehicle;
import com.fictiusclean.api.vehicle.VehicleController;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VehicleExpensesControllerTest {

    private final VehicleExpensesController vehicleExpensesController;

    private final VehicleExpensesService vehicleExpensesService;

    private final MockMvc mvc;

    private final ObjectMapper objectMapper;

    public VehicleExpensesControllerTest() {
        vehicleExpensesService = mock(VehicleExpensesService.class);
        vehicleExpensesController = new VehicleExpensesController(vehicleExpensesService);
        mvc = MockMvcBuilders.standaloneSetup(vehicleExpensesController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void getRankedGasExpenses_validGasPriceCityAndHighwayKilometers_returns() throws Exception {
        // given
        String gasPriceString = "4.32";
        String cityKilometersString = "30.21";
        String highwayKilometersString = "143.98";
        BigDecimal gasPrice = new BigDecimal(gasPriceString);
        BigDecimal cityKilometers = new BigDecimal(cityKilometersString);
        BigDecimal highwayKilometers = new BigDecimal(highwayKilometersString);
        Vehicle inputVehicle = Vehicle
                .builder()
                .cityGasLiterPerKilometerRate(BigDecimal.ONE)
                .highwayGasLiterPerKilometerRate(BigDecimal.TEN)
                .build();
        VehicleGasExpense expectedVehicleGasExpense = new VehicleGasExpense(inputVehicle, gasPrice, cityKilometers, highwayKilometers);
        String url = String.format(VehicleController.ROOT + "/expenses/gas?gasPrice=%s&cityKilometers=%s&highwayKilometers=%s", gasPriceString, cityKilometersString, highwayKilometersString);
        // when
        when(vehicleExpensesService.getRankedGasExpenses(gasPrice, cityKilometers, highwayKilometers)).thenReturn(Collections.singletonList(expectedVehicleGasExpense));
        // then
        MockHttpServletResponse response = mvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        TypeReference<List<VehicleGasExpense>> typeRef = new TypeReference<>() {
        };
        List<VehicleGasExpense> actualGasExpenses = objectMapper.readValue(response.getContentAsString(), typeRef);
        assertThat(actualGasExpenses).hasSize(1);
        assertThat(actualGasExpenses.get(0)).usingRecursiveComparison().isEqualTo(expectedVehicleGasExpense);
        verify(vehicleExpensesService).getRankedGasExpenses(gasPrice, cityKilometers, highwayKilometers);
    }

    @Test
    public void getRankedGasExpenses_gasPriceAndNullCityAndHighwayKilometers_returns() throws Exception {
        // given
        String gasPriceString = "4.32";
        BigDecimal gasPrice = new BigDecimal(gasPriceString);
        Vehicle inputVehicle = Vehicle
                .builder()
                .cityGasLiterPerKilometerRate(BigDecimal.ONE)
                .highwayGasLiterPerKilometerRate(BigDecimal.TEN)
                .build();
        VehicleGasExpense expectedVehicleGasExpense = new VehicleGasExpense(inputVehicle, gasPrice, null, null);
        String url = String.format(VehicleController.ROOT + "/expenses/gas?gasPrice=%s", gasPriceString);
        // when
        when(vehicleExpensesService.getRankedGasExpenses(gasPrice, null, null)).thenReturn(Collections.singletonList(expectedVehicleGasExpense));
        // then
        MockHttpServletResponse response = mvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        TypeReference<List<VehicleGasExpense>> typeRef = new TypeReference<>() {
        };
        List<VehicleGasExpense> actualGasExpenses = objectMapper.readValue(response.getContentAsString(), typeRef);
        assertThat(actualGasExpenses).hasSize(1);
        assertThat(actualGasExpenses.get(0)).usingRecursiveComparison().isEqualTo(expectedVehicleGasExpense);
        verify(vehicleExpensesService).getRankedGasExpenses(gasPrice, null, null);
    }

    @Test
    public void getRankedGasExpenses_nullGasPrice_badRequest() throws Exception {
        // given
        String url = String.format(VehicleController.ROOT + "/expenses/gas?cityKilometers=%s&highwayKilometers=%s", "4.1", "5.2");
        // then
        mvc.perform(get(url))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();
    }

    @Test
    public void getGasExpensesByVehicleId_validGasPriceCityAndHighwayKilometers_returns() throws Exception {
        // given
        Long vehicleId = 1L;
        String gasPriceString = "4.32";
        String cityKilometersString = "30.21";
        String highwayKilometersString = "143.98";
        BigDecimal gasPrice = new BigDecimal(gasPriceString);
        BigDecimal cityKilometers = new BigDecimal(cityKilometersString);
        BigDecimal highwayKilometers = new BigDecimal(highwayKilometersString);
        Vehicle inputVehicle = Vehicle
                .builder()
                .cityGasLiterPerKilometerRate(BigDecimal.ONE)
                .highwayGasLiterPerKilometerRate(BigDecimal.TEN)
                .build();
        VehicleGasExpense expectedVehicleGasExpense = new VehicleGasExpense(inputVehicle, gasPrice, cityKilometers, highwayKilometers);
        String url = String.format(VehicleController.ROOT + "/%d/expenses/gas?gasPrice=%s&cityKilometers=%s&highwayKilometers=%s", vehicleId, gasPriceString, cityKilometersString, highwayKilometersString);
        // when
        when(vehicleExpensesService.getGasExpensesByVehicleId(vehicleId, gasPrice, cityKilometers, highwayKilometers)).thenReturn(expectedVehicleGasExpense);
        // then
        MockHttpServletResponse response = mvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        VehicleGasExpense actualGasExpense = objectMapper.readValue(response.getContentAsString(), VehicleGasExpense.class);
        assertThat(actualGasExpense).usingRecursiveComparison().isEqualTo(expectedVehicleGasExpense);
        verify(vehicleExpensesService).getGasExpensesByVehicleId(vehicleId, gasPrice, cityKilometers, highwayKilometers);
    }

    @Test
    public void getGasExpensesByVehicleId_validGasPriceNullCityAndHighwayKilometers_returns() throws Exception {
        // given
        Long vehicleId = 1L;
        String gasPriceString = "4.32";
        BigDecimal gasPrice = new BigDecimal(gasPriceString);
        Vehicle inputVehicle = Vehicle
                .builder()
                .cityGasLiterPerKilometerRate(BigDecimal.ONE)
                .highwayGasLiterPerKilometerRate(BigDecimal.TEN)
                .build();
        VehicleGasExpense expectedVehicleGasExpense = new VehicleGasExpense(inputVehicle, gasPrice, null, null);
        String url = String.format(VehicleController.ROOT + "/%d/expenses/gas?gasPrice=%s", vehicleId, gasPriceString);
        // when
        when(vehicleExpensesService.getGasExpensesByVehicleId(vehicleId, gasPrice, null, null)).thenReturn(expectedVehicleGasExpense);
        // then
        MockHttpServletResponse response = mvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        VehicleGasExpense actualGasExpense = objectMapper.readValue(response.getContentAsString(), VehicleGasExpense.class);
        assertThat(actualGasExpense).usingRecursiveComparison().isEqualTo(expectedVehicleGasExpense);
        verify(vehicleExpensesService).getGasExpensesByVehicleId(vehicleId, gasPrice, null, null);
    }

    @Test
    public void getGasExpensesByVehicleId_nullGasPrice_badRequest() throws Exception {
        // given
        Long vehicleId = 1L;
        String url = String.format(VehicleController.ROOT + "/%d/expenses/gas", vehicleId);
        // then
        mvc.perform(get(url))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();
    }

    @Test
    public void getGasExpensesByVehicleId_nullVehicleId_badRequest() throws Exception {
        // given
        String gasPrice = "2.578";
        String url = String.format(VehicleController.ROOT + "/%d/expenses/gas?gasPrice=%s", null, gasPrice);
        // then
        mvc.perform(get(url))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();
    }
}
