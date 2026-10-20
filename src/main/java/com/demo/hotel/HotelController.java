package com.demo.hotel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api")
public class HotelController {
    private final HotelService hotelService;
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }
    @GetMapping("/hotels")
    public Map<String, List<String>> getHotels() {
        return Map.of("hotels", hotelService.getHotels());
    }
    @GetMapping("/hotels/{hotelName}/menu")
    public ResponseEntity<?> getHotelMenu(
            @PathVariable String hotelName) {
   	 String canonicalName =
                hotelService.getCanonicalHotelName(hotelName);
        if (canonicalName == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "error",
                            "Hotel not found: " + hotelName
                    ));
        }
        return ResponseEntity.ok(Map.of(
                "hotel", canonicalName,
                "meal", "Lunch",
                "items", hotelService.getMenu(canonicalName)
        ));
    }
    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }
}
