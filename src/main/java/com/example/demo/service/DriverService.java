package com.example.demo.service;

import com.example.demo.model.Delivery;
import com.example.demo.model.Driver;
import com.example.demo.repositories.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {

	@Autowired
    private DriverRepository driverRepository;

    // Method to add a new driver
    public Driver addDriver(Driver driver) {
        return driverRepository.save(driver);
    }
    
	public Driver saveDriver(Driver driver) {
		Driver d = driverRepository.save(driver);
		return d;
	}

    // Method to find drivers with null actualDelivery
    public List<Driver> getDriversWithNoActualDelivery() {
        return driverRepository.findByActualBusIsNull();
    }
    
    // Method to find drivers with null actualDelivery
    public List<Driver> getDriversAll() {
        return driverRepository.findAll();
    }
    
    public Driver findByMatricule(String matricule) {
    	return this.driverRepository.findById(matricule).orElse(null);
    }
    
}
