package com.aesl.geoloc.service;


import com.aesl.geoloc.dto.LocationDto;
import com.aesl.geoloc.model.Location;
import com.aesl.geoloc.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.GeoModule;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {


    @Autowired
    private LocationRepository locationRepository;

    public List<Location> getLocations() {
        return locationRepository.findAll();
    }


    public List<Location> getNumberOfLocations() {

        return locationRepository.findAll(PageRequest.of(0,2, Sort.by("id").descending())).getContent();
    }

    public ResponseEntity<?> saveLocation(LocationDto locationDto){
        Location location  = new Location();

        location.setLongitude(locationDto.getLongitude());
        location.setLatitude(locationDto.getLatitude());

        try {
            locationRepository.save(location);
            return ResponseEntity.ok().body("Location created");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public void reverseLookup(){
    }
}
