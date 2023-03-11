package com.example.firstapirest.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.firstapirest.models.ResponseDTO;
import com.example.firstapirest.services.ScrapperAlphaService;

@RestController
@RequestMapping(path = "/api", produces = "application/json")

public class ScrapperController {
    @Autowired
    private ScrapperAlphaService scraperService;

    @GetMapping(path = "/scrapp/vehicles/{vehicleModel}")
    public Set<ResponseDTO> getVehicleByModel(@PathVariable String vehicleModel) {
        return  scraperService.getVehiclesByModel(vehicleModel);
    }
    @GetMapping(path = "/scrapp/medicines/{medicineName}")
    public Set<ResponseDTO> getMedicinesByName(@PathVariable String medicineName) {
        return  scraperService.getMedicinesByName(medicineName);
    }
}
