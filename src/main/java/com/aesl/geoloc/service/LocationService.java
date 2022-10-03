package com.aesl.geoloc.service;


import com.aesl.geoloc.model.Location;
import com.aesl.geoloc.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {


    @Autowired
    private LocationRepository locationRepository;

    public List<Location> getLocations(){
        return locationRepository.findAll();
    }


    public Location getNumberOfLocations(){
        if(locationRepository.count() != 0){
            for(int i=0; i<100; i++){
                return locationRepository.findOne(Example.of());
            }

        return locationRepository.findOne()
    }


}
