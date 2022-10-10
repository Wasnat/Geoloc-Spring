package com.aesl.geoloc.service;


import com.aesl.geoloc.dto.LocationDto;
import com.aesl.geoloc.model.Location;
import com.aesl.geoloc.repository.LocationRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class LocationService {


    @Autowired
    private LocationRepository locationRepository;

    public List<Location> getLocations() {
        return locationRepository.findAll();
    }


    public List<Location> getNumberOfLocations(Integer n) {

        return locationRepository.findAll(PageRequest.of(0, n, Sort.by("id").descending())).getContent();
    }

    public ResponseEntity<?> saveLocation(LocationDto locationDto) {
        Location location = new Location();

        location.setLongitude(locationDto.getLongitude());
        location.setLatitude(locationDto.getLatitude());

        try {
//            System.out.println(reverseLookup(locationDto.getLongitude(), locationDto.getLatitude()));
            locationRepository.save(location);

            return ResponseEntity.ok(reverseLookup(locationDto.getLongitude(), locationDto.getLatitude()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    //get location name using
    public String reverseLookup(String lng, String lat) {


        String getNearestCitiesUrl = "https://geocodeapi.p.rapidapi.com/GetNearestCities?latitude="+lat+"&longitude="+lng+"&range=0";
        String getLargestCities = "https://geocodeapi.p.rapidapi.com/GetLargestCities?latitude="+lat+"&longitude="+lng+"&range=50000";
        String getLocationUrl = ("https://geocodeapi.p.rapidapi.com/GetTimezone?latitude="+lat+"&longitude="+lng);



        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(getNearestCitiesUrl))
                .header("X-RapidAPI-Key", "de89748bcemsh073eab99a324360p1c8d86jsn7bbb217a627b")
                .header("X-RapidAPI-Host", "geocodeapi.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
//        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return (response.body());
    }


}
