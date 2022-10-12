package com.aesl.geoloc.service;


import com.aesl.geoloc.dto.LocationDto;
import com.aesl.geoloc.dto.LocationResponseDTO;
import com.aesl.geoloc.dto.ResponseBodyDto;
import com.aesl.geoloc.model.Location;
import com.aesl.geoloc.repository.LocationRepository;
import com.google.gson.Gson;
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

    Location location = new Location();


    public List<Location> getLocations() {
        return locationRepository.findAll();
    }


    public List<Location> getNumberOfLocations(Integer n) {

        return locationRepository.findAll(PageRequest.of(0, n, Sort.by("id").descending())).getContent();
    }


    // Save Co-ordinates to database
    public LocationResponseDTO saveLocation(LocationDto locationDto) {

        LocationResponseDTO locationResponseDTO = new LocationResponseDTO();
        locationResponseDTO.setResponse(reverseLookup(locationDto.getLongitude(), locationDto.getLatitude()));

        location.setLongitude(locationDto.getLongitude());
        location.setLatitude(locationDto.getLatitude());

        try {
            System.out.println(reverseLookup(locationDto.getLongitude(), locationDto.getLatitude()));
            locationRepository.save(location);

            return locationResponseDTO;
        } catch (Exception e) {
            locationResponseDTO.setResponse(ResponseEntity.badRequest().body(e.getMessage()));
            return locationResponseDTO;
        }
    }


    //method for location Address and nearby cities and largest cities.
    public ResponseEntity<?> reverseLookup(String lng, String lat) {


//        // URLs for location from RapidAPI
//        String getNearestCitiesUrl = "https://geocodeapi.p.rapidapi.com/GetNearestCities?latitude="+lat+"&longitude="+lng+"&range=0";
//        String getLargestCities = "https://geocodeapi.p.rapidapi.com/GetLargestCities?latitude="+lat+"&longitude="+lng+"&range=50000";
//        String getLocationUrl = ("https://geocodeapi.p.rapidapi.com/GetTimezone?latitude="+lat+"&longitude="+lng);
//

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://forward-reverse-geocoding.p.rapidapi.com/v1/reverse?lat="+lat+"&lon="+lng+"&accept-language=en&polygon_threshold=0.0"))
                .header("X-RapidAPI-Key", "de89748bcemsh073eab99a324360p1c8d86jsn7bbb217a627b")
                .header("X-RapidAPI-Host", "forward-reverse-geocoding.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        Gson gson = new Gson();
        ResponseBodyDto responseBodyDto = gson.fromJson(response.body(), ResponseBodyDto.class);
        System.out.println(responseBodyDto);
        return ResponseEntity.ok(responseBodyDto);
    }

}
