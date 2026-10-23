package com.demo.hotel;
import org.springframework.stereotype.Service;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
@Service
public class HotelService {
    private final Map<String, List<String>> menus =
            new LinkedHashMap<>();
    public HotelService() {
        menus.put("Taj", List.of(
                "Paneer Butter Masala",
                "Chicken Dum Biryani",
                "Dal Tadka",
                "Butter Naan",
				"Gulab Jamun"
        ));
        menus.put("ITC Gardenia", List.of(
                "Chicken Biryani",
                "Veg Pulao",
                "Dal Makhani",
                "Gulab Jamun"
        ));
        menus.put("Leela Palace", List.of(
                "Mushroom Soup",
                "Paneer Tikka",
                "Jeera Rice",
                "Butter Naan"
        ));
	menus.put("Oberoi", List.of(
                "Tomato Soup",
                "Veg Biryani",
                "Palak Paneer",
                "Roti"
        ));
        menus.put("Marriott", List.of(
                "Sweet Corn Soup",
                "Fried Rice",
                "Veg Manchurian",
                "Ice Cream"
        ));
    }
    public List<String> getHotels() {
        return List.copyOf(menus.keySet());
    }
    public List<String> getMenu(String hotelName) {
        return menus.entrySet()
                .stream()
                .filter(entry -> entry.getKey()
                        .equalsIgnoreCase(hotelName))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }
    public String getCanonicalHotelName(String hotelName) {
        return menus.keySet()
                .stream()
                .filter(name -> name.equalsIgnoreCase(hotelName))
                .findFirst()
                .orElse(null);
    }
}


