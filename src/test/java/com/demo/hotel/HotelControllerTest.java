package com.demo.hotel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
class HotelControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    void shouldReturnHotels() throws Exception {
        mockMvc.perform(get("/api/hotels"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hotels.length()").value(5));
    }
    @Test
    void shouldReturnTajLunchMenu() throws Exception {
        mockMvc.perform(get("/api/hotels/Taj/menu"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hotel").value("Taj"))
                .andExpect(jsonPath("$.meal").value("Lunch"))
                .andExpect(jsonPath("$.items.length()").value(5));
    }
    @Test
    void shouldReturnNotFoundForUnknownHotel() throws Exception {
        mockMvc.perform(get("/api/hotels/Unknown/menu"))
                .andExpect(status().isNotFound());
    }
    @Test
    void shouldReturnHealthyStatus() throws Exception {
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }
}
