package com.aesl.geoloc.controller;


import com.aesl.geoloc.dto.LocationDto;
import com.aesl.geoloc.model.Location;
import com.aesl.geoloc.service.LocationService;
import org.hibernate.bytecode.internal.bytebuddy.BytecodeProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/location")
public class LocationController {
    @Autowired
    private LocationService locationService;


    @GetMapping("/viewAll")
    public List<Location> getAllLocations(){
        return locationService.getLocations();
    }


    @GetMapping("/view")
    public List<Location> getLocationById(){
        return locationService.getNumberOfLocations();
    }


    @PostMapping("/new")
    public ResponseEntity<?> saveNewLocation(@RequestBody LocationDto locationDto){
        return locationService.saveLocation(locationDto);

    }

}
