package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.config.JwtTokenUtil;
import com.example.demo.model.Driver;
import com.example.demo.service.BusService;
import com.example.demo.service.DeliveryService;
import com.example.demo.service.DriverService;
import com.example.demo.service.PackageService;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true") // Allow your React app's origin
public class LocationController {
	
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
	@Autowired
	private DriverService driverService;
	
	@Autowired
	private BusService busService;
	
	@Autowired
	private DeliveryService deliveryService;
	
	@Autowired
	private PackageService packageService;

    // Endpoint that receives city updates
    @PostMapping("/location-test")
    public ResponseEntity<?> receiveLocation(
            @RequestHeader("Authorization") String authorization,
            @RequestBody Map<String, String> payload,
            HttpServletRequest request
    ) {
        try {
            // Extract token from header (Bearer xxx)
            String token = authorization.replace("Bearer ", "");

            // For demonstration, print the token and city
            String city = payload.get("city");
            System.out.println("Received city: " + city);
            String matricule = jwtTokenUtil.getMatriculeFromToken(token);

            System.out.println("matricule: " + matricule);
            Driver d = driverService.findByMatricule(matricule);
            d.getActualBus().setCity(city);
            driverService.saveDriver(d);

            // Here you could validate the token, store city in DB, etc.

            return ResponseEntity.ok("City received: " + city);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping("/location-params/{tracking-param}")
    public Map<String, String> search(@PathVariable("tracking-param") String value) {
        String city;

        if (value.startsWith("BUS-")) {
            String busId = value.substring(4);
            city = busService.loadBusById(busId).getCity();
        } else if (value.startsWith("LIV-")) {
            String deliveryId = value.substring(4);
            city = deliveryService.findByLabel_livraison(deliveryId).getBus().getCity();
        } else if (value.startsWith("COL-")) {
            String packageId = value.substring(4);
            city = packageService.findByNumSerie(packageId).getLabel_livrasion().getBus().getCity();
        } else {
            city = "Unknown";
        }

        return Map.of("city", city);
    }
    
    
}
