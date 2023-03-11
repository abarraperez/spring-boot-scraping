package com.example.firstapirest.services;

import java.util.List;
import java.util.Set;

import com.example.firstapirest.models.ResponseDTO;

public interface ScrapperAlphaService {
    Set<ResponseDTO> getVehiclesByModel(String vehicleModel);

    Set<ResponseDTO> getMedicinesByName(String medicineName);
}
